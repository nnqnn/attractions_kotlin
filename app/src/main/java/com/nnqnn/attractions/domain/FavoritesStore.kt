package com.nnqnn.attractions.domain

import com.nnqnn.attractions.data.local.dao.FavoriteDao
import com.nnqnn.attractions.data.local.entity.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesStore(private val dao: FavoriteDao) {
    suspend fun load(): Set<Int> = withContext(Dispatchers.IO) {
        dao.getAll().map { it.attractionId }.toSet()
    }

    suspend fun add(id: Int) = withContext(Dispatchers.IO) {
        dao.insert(FavoriteEntity(id))
    }

    suspend fun remove(id: Int) = withContext(Dispatchers.IO) {
        dao.delete(FavoriteEntity(id))
    }
}

