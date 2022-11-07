package com.example.weatherapp.network

import com.example.weatherapp.utils.Constants.Companion.WEATHER_API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    fun getRetroFitInstanceForWeatherApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WEATHER_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}