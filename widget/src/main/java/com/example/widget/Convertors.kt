package com.example.widget

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import java.time.Duration
import java.util.Locale

class Convertors @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimeToString(time: LocalTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH'h'mm"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToTime(time: String): LocalTime {
        val timeLocalTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH'h'mm"))
        return timeLocalTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToString(date: LocalDate): String {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault()).format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDurationToString(duration: Duration): String {
        var formattedDuration = "-"
        val hours = duration.toHours()
        val minutes = (duration.toMinutes() % 60)
        val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
        formattedDuration = if (hours.toInt() == 0) "${minutes}min" else "${hours}h${minutesString}"
        return formattedDuration
    }


}