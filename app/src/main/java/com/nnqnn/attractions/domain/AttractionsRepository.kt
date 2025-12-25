package com.nnqnn.attractions.domain

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.AttractionCategory
import com.nnqnn.attractions.model.MapBounds

class AttractionsRepository(private val context: Context) {

    private val data: List<Attraction> by lazy { loadFromAssets() }
    private val boundsCache: MapBounds by lazy { computeBounds(data) }

    fun getAll(): List<Attraction> = data

    fun bounds(): MapBounds = boundsCache

    fun filter(
        query: String,
        category: AttractionCategory?,
        favorites: Set<Int>,
        favoritesOnly: Boolean
    ): List<Attraction> {
        val trimmed = query.trim()
        return data.filter { attraction ->
            val matchesQuery = if (trimmed.isBlank()) {
                true
            } else {
                attraction.name.contains(trimmed, ignoreCase = true) ||
                    attraction.address.contains(trimmed, ignoreCase = true) ||
                    attraction.tags.any { it.contains(trimmed, ignoreCase = true) }
            }
            val matchesCategory = category?.let { attraction.category == it } ?: true
            val matchesFav = if (favoritesOnly) favorites.contains(attraction.id) else true
            matchesQuery && matchesCategory && matchesFav
        }
    }

    private fun loadFromAssets(): List<Attraction> {
        val json = context.assets.open("attractions.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Attraction>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun computeBounds(items: List<Attraction>): MapBounds {
        return MapBounds(
            minLat = items.minOf { it.coords.getOrNull(0) ?: 0.0 } - 0.01,
            maxLat = items.maxOf { it.coords.getOrNull(0) ?: 0.0 } + 0.01,
            minLon = items.minOf { it.coords.getOrNull(1) ?: 0.0 } - 0.01,
            maxLon = items.maxOf { it.coords.getOrNull(1) ?: 0.0 } + 0.01
        )
    }
}

