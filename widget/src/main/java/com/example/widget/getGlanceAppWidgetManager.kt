package com.example.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.time_tracking_app.database.DayDao
import dagger.hilt.android.EntryPointAccessors

// Helper API to extract dependency from EntryPointAccessors //
fun getGlanceAppWidgetManager(context: Context): GlanceAppWidgetManager {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context, WidgetEntryPoint::class.java
    )
    return hiltEntryPoint.glanceAppWidgetManager()
}

fun getWidgetViewModel(context: Context): WidgetViewModel {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context, WidgetEntryPoint::class.java
    )
    return hiltEntryPoint.widgetViewModel()
}

fun getDayDao(context: Context): DayDao {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context, WidgetEntryPoint::class.java
    )
    return hiltEntryPoint.dayDao()
}

fun getDayUseCase(context: Context): UseCase {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context, WidgetEntryPoint::class.java
    )
    return hiltEntryPoint.dayUseCase()
}
