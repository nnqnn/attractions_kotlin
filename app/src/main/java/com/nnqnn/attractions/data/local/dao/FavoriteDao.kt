package com.nnqnn.attractions.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nnqnn.attractions.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavoriteEntity)

    @Delete
    suspend fun delete(entity: FavoriteEntity)
}

