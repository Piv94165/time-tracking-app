package com.example.time_tracking_app.composables.weekPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.composables.DayTrackingContent
import com.example.time_tracking_app.composables.EditDay
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.composables.weekPage.topNavigation.TopNavBar
import com.example.time_tracking_app.utils.Convertors
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekPage(
    allDays: List<DayEntity>,
    onClickDay: (DayEntity) -> Unit,
    weekOfYear: Int,
    year: Int,
    workingHours: String,
    onPreviewWeekClicked: () -> Unit,
    onNextWeekClicked: () -> Unit,
    convertors: Convertors,
) {
    val dayEntityClicked = remember {
        mutableStateOf<DayEntity?>(null)
    }
    val timeEditSheetIsShown = remember { mutableStateOf(false) }

    val modalBottomSheetState = rememberModalBottomSheetState()

    if (timeEditSheetIsShown.value) {
        ModalBottomSheet(
            onDismissRequest = { timeEditSheetIsShown.value = false },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            EditDay(
                dayClicked = dayEntityClicked.value,
                timeEditSheetIsShown = timeEditSheetIsShown,
                convertors = convertors,
                insertNewDay = { day ->
                    onClickDay(day)
                },
            )
        }
    }

    Column {
        TopNavBar(
            weekNumber = weekOfYear,
            year = year,
            workingHours = workingHours,
            onPreviousWeekClicked = onPreviewWeekClicked,
            onNextWeekClicked = onNextWeekClicked,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            /*item {
                TopNavBar(
                    weekNumber = weekOfYear,
                    year = year,
                    onPreviousWeekClicked = onPreviewWeekClicked,
                    onNextWeekClicked = onNextWeekClicked,
                )
            }*/
            items(allDays) { day ->
                DayTrackingContent(day, convertors) {
                    timeEditSheetIsShown.value = true
                    dayEntityClicked.value = day
                }
            }
        }
    }
    /*
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopNavBar(
                weekNumber = weekOfYear,
                year = year,
                onPreviousWeekClicked = onPreviewWeekClicked,
                onNextWeekClicked = onNextWeekClicked,
            )
        }
        items(allDays) { day ->
            DayTrackingContent(day, convertors) {
                timeEditSheetIsShown.value = true
                dayEntityClicked.value = day
            }
        }
    }

     */
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewWeekPage() {
    WeekPage(
        allDays = listOf(
            DayEntity(
                date = LocalDate.now(),
                startTime = LocalTime.of(9, 32)
            ),
            DayEntity(date = LocalDate.now().plusDays(1))
        ),
        workingHours = "10h30",
        convertors = Convertors(),
        onClickDay = { _ -> },
        onPreviewWeekClicked = {},
        onNextWeekClicked = {},
        weekOfYear = 23,
        year = 2024,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewEmptyWeekPage() {
    WeekPage(
        allDays = emptyList(),
        workingHours = "0 min",
        convertors = Convertors(),
        onClickDay = { _ -> },
        onPreviewWeekClicked = {},
        onNextWeekClicked = {},
        weekOfYear = 23,
        year = 2024,
    )
}