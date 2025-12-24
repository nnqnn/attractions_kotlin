package com.nnqnn.attractions.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nnqnn.attractions.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_cache WHERE id = 0")
    suspend fun getCached(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeatherEntity)
}

