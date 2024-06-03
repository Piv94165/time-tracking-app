package com.example.time_tracking_app.composables.todayPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datasource.database.DayEntity
import com.example.time_tracking_app.R
import com.example.time_tracking_app.utils.Convertors

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodaySummary(
    day: DayEntity,
    convertors: Convertors,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 200.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.day_summary_title), fontSize = 30.sp)
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = stringResource(id = R.string.start_day_hour, "${day.startTime?.let { convertors.convertTimeToString(it) }}"))
            Text(text = stringResource(id = R.string.end_day_hour, "${day.endTime?.let { convertors.convertTimeToString(it) }}"))
            Text(text = stringResource(id = R.string.working_hours, day.stringDuration()))
        }
    }

}