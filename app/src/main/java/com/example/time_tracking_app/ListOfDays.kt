package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


@Composable
    @RequiresApi(Build.VERSION_CODES.O)
    fun Content(listOfDays : List<DayTrackingType>, onClick: () -> Unit, date: MutableState<String>, startTime: MutableState<String>, endTime: MutableState<String>) {
        listOfDays.forEach { day ->
            DayTrackingContent(DayTrackingType(day.date, day.startTime, day.endTime), onClick, date, startTime, endTime)
        }
    }