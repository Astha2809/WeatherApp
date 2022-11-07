package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.WeatherRepository
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    var weatherRepository: WeatherRepository = WeatherRepository()
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
                    getWeatherInfoFromRepo(
                        location?.latitude.toString(),
                        location?.longitude.toString()
                    )

                }
            }
        setContentView(mBinding.root)

    }


    private suspend fun getWeatherInfoFromRepo(latitude: String, longitude: String) {
        val aa = weatherRepository.getWeatherInfo(latitude, longitude)
        CoroutineScope(Dispatchers.Main).launch {
            mBinding.tvCurrentTemp.text = aa.body()?.currentWeather?.temperature.toString()
        }
    }

}