package com.example.weatherapp.model

import com.example.weatherapp.network.RetroInstance
import com.example.weatherapp.network.WebService
import retrofit2.Response

class WeatherRepository {
    private var webService: WebService = RetroInstance().getRetroFitInstanceForWeatherApi().create(WebService::class.java)
     suspend fun getWeatherInfo(latitude:String, longitude:String): Response<WeatherApiResponse> =
        webService.getCurrentTemperature(latitude,longitude)

}