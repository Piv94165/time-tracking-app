package com.example.time_tracking_app.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.room.Room
import com.example.time_tracking_app.DayRepository
import com.example.time_tracking_app.database.AppDatabase
import com.example.time_tracking_app.network.PublicHolidaysService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// This module provide Glance Widget manager dependency //
@Module
@InstallIn(SingletonComponent::class)
object WidgetModule {

    @Provides
    @Singleton
    fun providesGlanceWidgetManager(@ApplicationContext context: Context): GlanceAppWidgetManager = GlanceAppWidgetManager(context)




}