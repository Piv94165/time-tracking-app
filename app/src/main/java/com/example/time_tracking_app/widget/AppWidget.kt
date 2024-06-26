package com.example.time_tracking_app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.glance.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.IconImageProvider
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.example.time_tracking_app.R
import com.example.time_tracking_app.UseCase
import com.example.time_tracking_app.database.DayDao
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.utils.Convertors
import com.example.time_tracking_app.widget.composables.EndMyDayWidget
import com.example.time_tracking_app.widget.composables.StartMyDayWidget
import com.example.time_tracking_app.widget.composables.TodaySummaryWidget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class AppWidget : GlanceAppWidget() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext
        val dayDao = getDayDao(appContext)
        val usecase = getDayUseCase(appContext)


        provideContent {
            GlanceTheme {
                Content(dayDao, context, usecase)
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Content(dayDao: DayDao, context: Context, useCase: UseCase) {
        // TODO : plus sensé utiliser DayDao sachant que tu as le UseCase
        val convertors = Convertors()
        val dayFromUseCase by useCase.getDayByDate(LocalDate.now()).collectAsState(initial = null)
        val coroutineScope = rememberCoroutineScope()

        fun editDay(updatedDay: DayEntity) {
            coroutineScope.launch(Dispatchers.IO) {
                dayDao.insertOrUpdateDate(updatedDay)
                AppWidget().updateAll(context)
            }
        }

        Column(
            modifier = GlanceModifier.background(Color.White).padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
                Text(
                    modifier = GlanceModifier,
                    text = convertors.convertDateToString(dayFromUseCase?.date ?: LocalDate.now())
                )

                when (val localDayFromUseCase = dayFromUseCase) {
                    null -> {
                        coroutineScope.launch(Dispatchers.IO) {
                            useCase.createDayEntityForLocalDateIfDoesNotExist(LocalDate.now())
                            AppWidget().updateAll(context)
                        }
                    }

                    else -> {
                        if (localDayFromUseCase.startTime == null) {
                            StartMyDayWidget(day = localDayFromUseCase, context) { updatedDay ->
                                editDay(updatedDay)
                            }
                        } else if (localDayFromUseCase.endTime == null) {
                            EndMyDayWidget(
                                day = localDayFromUseCase,
                                context,
                                editDay = { updatedDay: DayEntity ->
                                    editDay(updatedDay)
                                })
                        } else {
                            TodaySummaryWidget(localDayFromUseCase, context, convertors)
                        }
                    }
                }
        }
    }
}