package com.nnqnn.attractions.domain

import com.nnqnn.attractions.data.MockAttractions
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.AttractionCategory

class AttractionsRepository {
    private val source = MockAttractions.items

    fun getAll(): List<Attraction> = source

    fun filter(
        query: String,
        category: AttractionCategory?,
        favorites: Set<Int>,
        favoritesOnly: Boolean
    ): List<Attraction> {
        val trimmed = query.trim()
        return source.filter { attraction ->
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
}

