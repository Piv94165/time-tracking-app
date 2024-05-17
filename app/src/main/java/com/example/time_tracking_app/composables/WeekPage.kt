package com.example.time_tracking_app.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.composables.DayTrackingContent
import com.example.time_tracking_app.database.DayEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekPage(
    listOfDays: State<List<DayEntity>>,
    timeEditSheetIsShown: MutableState<Boolean>,
    dayEntityClicked: MutableState<DayEntity?>,

    ) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOfDays.value) { day ->
                val dayEntityTrackingType = DayEntity(
                    day.date,
                    day.startTime,
                    day.endTime
                )
                DayTrackingContent(
                    dayEntityTrackingType
                ) {
                    timeEditSheetIsShown.value = true
                    dayEntityClicked.value = dayEntityTrackingType
                }
            }
        }

    }
}