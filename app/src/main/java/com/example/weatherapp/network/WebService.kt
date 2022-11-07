package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("/v1/forecast?")
     suspend fun getCurrentTemperature(@Query("latitude") latitude : String,@Query("longitude") longitude : String,@Query("current_weather") current_weather: Boolean =true) : Response<WeatherApiResponse>
}