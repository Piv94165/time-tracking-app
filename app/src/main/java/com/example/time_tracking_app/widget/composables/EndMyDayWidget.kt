package com.example.time_tracking_app.widget.composables

import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.example.time_tracking_app.database.DayEntity
import java.time.LocalTime

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun EndMyDayWidget(
    day: DayEntity,
    editDay: (DayEntity) -> Unit,
) {

    fun endDay() {
        day.endTime= LocalTime.now()
        editDay(day)
    }

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        /*
        Icon(
            Icons.Default.PlayArrow,
            contentDescription = "commencer ma journée",
            modifier = GlanceModifier
                .padding(bottom = 12.dp)
        )
         */
        Text(
            text = "Vous avez commencé votre journée à ${day.startTime}",
            modifier = GlanceModifier
                .padding(bottom = 12.dp)
        )
        Spacer(GlanceModifier.height(12.dp))
        Button(
            text = "Terminer",
            onClick = { endDay() }
        )

    }

    
}