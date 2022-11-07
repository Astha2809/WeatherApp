package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.utils.Resource
import retrofit2.Response

class WeatherViewModel:ViewModel() {
    private var weatherRepository:WeatherRepository= WeatherRepository()
     val currentWeather: MutableLiveData<Resource<WeatherApiResponse>> = MutableLiveData()
    var weatherApiResponse:WeatherApiResponse?=null

    suspend fun getWeatherInfoFromRepo(latitude:String,longitude:String,startDate:String,endDate:String){
        val response: Response<WeatherApiResponse> = weatherRepository.getWeatherInfo(latitude, longitude,startDate, endDate)
        currentWeather.postValue(handlingNewMovieResponse(response))
        //weatherRepository.getWeatherInfo(latitude,longitude)

    }
    private fun handlingNewMovieResponse(response: Response<WeatherApiResponse>): Resource<WeatherApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (weatherApiResponse == null) {
                    weatherApiResponse = resultResponse
                    return Resource.Success(weatherApiResponse ?: resultResponse)
                }
            }
        }
        return Resource.Error(response.message())
    }
}