package com.nnqnn.attractions.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nnqnn.attractions.data.local.dao.FavoriteDao
import com.nnqnn.attractions.data.local.dao.WeatherDao
import com.nnqnn.attractions.data.local.entity.FavoriteEntity
import com.nnqnn.attractions.data.local.entity.WeatherEntity

@Database(
    entities = [FavoriteEntity::class, WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun weatherDao(): WeatherDao
}

