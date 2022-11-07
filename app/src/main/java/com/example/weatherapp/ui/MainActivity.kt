package com.example.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.utils.Resource
import com.example.weatherapp.utils.TimeUtils
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Log.i("Location", location?.longitude.toString())

                CoroutineScope(Dispatchers.IO).launch {

                    // getWeatherInfoFromRepo("52.52", "13.41")
                    TimeUtils.endDate()?.let {
                        getWeatherInfoFromViewModel(
                            location?.latitude.toString(),
                            location?.longitude.toString(),
                            TimeUtils.today(),
                            it
                        )
                    }

                }
            }
        setContentView(mBinding.root)

    }



    private suspend fun getWeatherInfoFromViewModel(latitude: String, longitude: String, startDate:String, endDate:String) {
        weatherViewModel.getWeatherInfoFromRepo(latitude, longitude,startDate, endDate)
        CoroutineScope(Dispatchers.Main).launch {
            weatherViewModel.currentWeather.observe(this@MainActivity){response->
                when(response){
                    is Resource.Success->{
                        val currentWeatherDetails=response.data?.currentWeather
                        mBinding.tvCurrentTemp.text= "Current Temp:${currentWeatherDetails?.temperature.toString()}"
                        mBinding.tvCurrentTime.text="Time:${currentWeatherDetails?.time.toString()}"
                        mBinding.tvWindSpeed.text="Windspeed:${currentWeatherDetails?.windspeed.toString()}"
                    }
                    is Resource.Error->{
                        Log.i("Error occured",response.message.toString())
                    }

                    else -> {
                        Log.i("Error occured","else")
                    }
                }

            }
        }
    }

}