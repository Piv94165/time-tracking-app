package com.example.time_tracking_app.widget.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.room.util.TableInfo
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.utils.Convertors

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodaySummaryWidget(
    day: DayEntity,
    convertors: Convertors,
) {

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Heure d'embauche : ${day.startTime}")
        Text(text = "Heure de débauche : ${day.endTime}")
        Text(text = "Temps de travail : ${day.stringDuration()} travaillées")
        Text(text = "C'est le moment de se reposer !")
    }


}