package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

data class DayTrackingType(
    val date: LocalDate,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val modifier: Modifier = Modifier
)

class DayTracking(
    private val dayTracking: DayTrackingType
) {
    private val date: LocalDate = dayTracking.date
    private val startTime: LocalTime? = dayTracking.startTime
    private val endTime: LocalTime? = dayTracking.endTime
    private val modifier: Modifier = dayTracking.modifier

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Content() {
        var formattedDuration = "-"
        if (startTime != null && endTime != null) {
            val duration = Duration.between(startTime, endTime)
            val hours = duration.toHours()
            val minutes = (duration.toMinutes() % 60)
            formattedDuration = "${hours}h${minutes}"
        }
        val today = LocalDate.now()

        val backgroundColor = when {
            date < today -> Color(0xFFC1C0FF) // previous days
            date == today -> Color(0xFFFFC1C0) // today
            else -> Color(0xFFE5E7E9) // next days
        }

        Surface(
            color = backgroundColor,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            shape = RoundedCornerShape(10),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = date.toString(),
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Durée : $formattedDuration",
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp)) // Ajouter un espacement vertical
                Text(text = "Heure d'embauche : ${startTime ?: " - "}")
                Spacer(modifier = Modifier.height(8.dp)) // Ajouter un espacement vertical
                Text(text = "Heure de débauche : ${endTime ?: " - "}")
            }
        }
    }
}
