package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Convertors {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimeToString(time: LocalTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH'h'mm"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToTime(time: String): LocalTime {
        val timeLocalTime = LocalTime.parse(time,DateTimeFormatter.ofPattern("HH'h'mm"))
        return timeLocalTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToString(date: LocalDate): String {

        return "${date.dayOfMonth} ${date.month} ${date.year}"
    }


}