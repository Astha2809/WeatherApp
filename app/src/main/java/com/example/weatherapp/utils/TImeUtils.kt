package com.example.weatherapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun today(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
    fun endDate(): String? {
       return addDays(today(),4)
    }
    fun secondDay():String?{
        return addDays(today(),2)
    }
    fun thirdDay():String?{
        return addDays(today(),3)
    }
    fun fourthDay():String?{
        return addDays(today(),4)
    }
    fun addDays(startDate: String?, numberOfDays: Int): String? {
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

       // SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val c = Calendar.getInstance()
        try {
            c.time = dateFormat.parse(startDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        c.add(Calendar.DAY_OF_WEEK, numberOfDays)
        return dateFormat.format(c.time)
    }
}