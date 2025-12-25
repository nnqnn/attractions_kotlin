package com.nnqnn.attractions.domain

import com.nnqnn.attractions.data.local.dao.WeatherDao
import com.nnqnn.attractions.data.local.entity.WeatherEntity
import com.nnqnn.attractions.model.WeatherInfo
import com.nnqnn.weatherlib.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
    ) {

    suspend fun getWeather(): WeatherInfo = withContext(Dispatchers.IO) {
        val remote = runCatching { api.getWeather() }.getOrNull()
        val current = remote?.current_weather
        if (current?.temperature != null && current.windspeed != null && current.time != null) {
            val info = WeatherInfo(
                temperature = current.temperature!!,
                windSpeed = current.windspeed!!,
                time = current.time!!
            )
            weatherDao.insert(
                WeatherEntity(
                    temperature = info.temperature,
                    windSpeed = info.windSpeed,
                    time = info.time
                )
            )
            info
        } else {
            weatherDao.getCached()?.let {
                WeatherInfo(it.temperature, it.windSpeed, it.time)
            } ?: WeatherInfo(0.0, 0.0, "нет данных")
        }
    }
}

