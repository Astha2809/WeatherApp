package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.PastDaysItemsBinding

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private var maxTempList: ArrayList<Double> = arrayListOf()
    private var minTempList: ArrayList<Double> = arrayListOf()
    private var dates : ArrayList<String> = arrayListOf()
    lateinit var mBinding: PastDaysItemsBinding
    inner class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(maxTemp: Double,minTemp:Double,dates:String) {
            mBinding.tvMaxTemp.text = "Max Temp:${maxTemp}"+"C"
            mBinding.tvMinTemp.text= "Min Temp:${minTemp}"+"C"
            mBinding.tvDates.text=dates



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        mBinding = PastDaysItemsBinding.inflate(layoutInflater, parent, false)
        return WeatherViewHolder(mBinding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
       holder.bind(maxTempList.get(position),minTempList.get(position),dates.get(position))
    }

    override fun getItemCount(): Int {
        return maxTempList.size
    }
    fun addMaxTempData(list:ArrayList<Double>) {
        this.maxTempList.clear()
        this.maxTempList = list
        notifyDataSetChanged()

    }
    fun addMinTempData(list:ArrayList<Double>) {
        this.minTempList.clear()
        this.minTempList = list
        notifyDataSetChanged()

    }

    fun addListOfDates(list: ArrayList<String>){
        this.dates.clear()
        this.dates=list
        notifyDataSetChanged()
    }

}