package com.nnqnn.attractions.network

import retrofit2.http.GET

data class WeatherDto(
    val current_weather: CurrentWeatherDto?
)

data class CurrentWeatherDto(
    val temperature: Double?,
    val windspeed: Double?,
    val time: String?
)

interface WeatherApi {
    @GET("v1/forecast?latitude=55.79&longitude=49.12&current_weather=true")
    suspend fun getWeather(): WeatherDto
}

