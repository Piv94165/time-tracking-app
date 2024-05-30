package com.example.time_tracking_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeTrackingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }

    
}