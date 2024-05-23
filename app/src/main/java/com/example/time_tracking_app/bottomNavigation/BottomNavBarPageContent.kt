package com.example.time_tracking_app.bottomNavigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.time_tracking_app.composables.todayPage.TodayPage
import com.example.time_tracking_app.composables.todayPage.TodayPageViewModel
import com.example.time_tracking_app.composables.weekPage.WeekPage
import com.example.time_tracking_app.composables.weekPage.WeekPageViewModel
import com.example.time_tracking_app.database.DayEntity
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavBarPageContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
) {

    NavHost(
        modifier = androidx.compose.ui.Modifier.padding(paddingValues),
        navController = navController,
        startDestination = "today"
    ) {
        composable("today") {
            val viewModel = hiltViewModel<TodayPageViewModel>()
            val today by viewModel.currentDay.collectAsState(null)
            today?.let { day ->
                TodayPage(
                    day = day,
                    editDay = { updatedDay: DayEntity ->
                        Log.d("", "Hello from lambda edit day")
                        viewModel.editDay(
                            updatedDay
                        )
                    },
                    convertors = viewModel.providesConvertors(),
                )
            }
        }
        composable("week") {
            val viewModel = hiltViewModel<WeekPageViewModel>()
            val weekOfYear by viewModel.selectedWeek.collectAsState(
                initial = LocalDate.now().get(
                    WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
                )
            )
            val year by viewModel.selectedYear.collectAsState(initial = LocalDate.now().year)
            val days by viewModel.allDaysForWeek().collectAsState(initial = emptyList())
            WeekPage(
                allDays = days,
                onClickDay = { day -> viewModel.editDay(day) },
                convertors = viewModel.providesConvertors(),
                weekOfYear = weekOfYear,
                year = year,
                onPreviewWeekClicked = {
                    viewModel.loadPreviousWeek()
                },
                onNextWeekClicked = {
                    viewModel.loadNextWeek()
                }
            )
        }
    }
}