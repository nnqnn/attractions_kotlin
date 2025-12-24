package com.nnqnn.attractions.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val id: Int = 0,
    val temperature: Double,
    val windSpeed: Double,
    val time: String
)

