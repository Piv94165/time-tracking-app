package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

class ListOfDays ( private val listOfDays : List<DayTrackingType>) {

    @Composable
    @RequiresApi(Build.VERSION_CODES.O)
    fun Content() {
        listOfDays.forEach { day ->
            val dayContent = DayTracking(DayTrackingType(day.date, day.startTime, day.endTime))
            dayContent.Content()
        }
    }

}