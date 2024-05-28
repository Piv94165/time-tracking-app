package com.example.time_tracking_app.widget.composables

import android.graphics.Paint.Align
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.example.time_tracking_app.database.DayEntity
import java.time.LocalDate
import java.time.LocalTime

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun StartMyDayWidget(
    day: DayEntity,
    editDay: (DayEntity) -> Unit,
) {


    fun startDay() {
        day.startTime = LocalTime.now()
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
            text = "Bonjour, une nouvelle journée commence !",
            modifier = GlanceModifier
                .padding(bottom = 12.dp),
        )
        Button(
            text = "Commencer",
            onClick = { startDay() }
        )

    }

}