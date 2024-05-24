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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.time_tracking_app.database.DayEntity
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
        Text(text = "Résumé de ma journée", fontSize = 30.sp)
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Heure d'embauche : ${day.startTime?.let { convertors.convertTimeToString(it) }}")
            Text(text = "Heure de débauche : ${day.endTime?.let { convertors.convertTimeToString(it) }}")
            Text(text = "Temps de travail : ${day.stringDuration()}")
        }
    }

}