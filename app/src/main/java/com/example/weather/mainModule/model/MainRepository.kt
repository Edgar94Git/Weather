package com.example.weather.mainModule.model

import com.example.weather.common.entities.WeatherForecastEntity

class MainRepository {
    private val remoteDataBase = RemoteDataBase()
    suspend fun getWeatherAndForecast( lat: Double, lon: Double, appId: String, units: String, lang: String):
            WeatherForecastEntity = remoteDataBase.getWeatherForecastByCoordinates(lat, lon, appId, units, lang)
}