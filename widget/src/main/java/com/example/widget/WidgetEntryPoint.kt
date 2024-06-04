package com.example.widget

import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.time_tracking_app.database.DayDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Custom EntryPoint, to inject dependencies //
@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun glanceAppWidgetManager(): GlanceAppWidgetManager

    fun widgetViewModel(): WidgetViewModel

    fun dayDao(): DayDao

    fun dayUseCase(): UseCase
}