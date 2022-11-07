package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.utils.Resource
import com.example.weatherapp.utils.TimeUtils
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


    fun createPastMinTempList(response:WeatherApiResponse):ArrayList<Double>{
        val list:ArrayList<Double> = arrayListOf()
//        for(i in 0..5)
//            response.daily.temperature2mMax.get(i).let { list.add(it) }
        response.daily.temperature2mMin.forEach {
            list.add(it)
        }
        return list
    }

    fun createPastMaxTempList(response:WeatherApiResponse):ArrayList<Double>{
        val list:ArrayList<Double> = arrayListOf()
//        for(i in 0..5)
//            response.daily.temperature2mMax.get(i).let { list.add(it) }
        response.daily.temperature2mMax.forEach {
            list.add(it)
        }
        return list
    }

    fun createListOfDates():ArrayList<String>{
        val list:ArrayList<String> = arrayListOf()
        list.add(TimeUtils.today())
        TimeUtils.secondDay()?.let { list.add(it) }
        TimeUtils.thirdDay()?.let { list.add(it) }
        TimeUtils.fourthDay()?.let { list.add(it) }
        TimeUtils.endDate()?.let { list.add(it) }
        return list
    }
}