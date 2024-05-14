package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class DayTrackingType(
    val date: LocalDate,
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayTrackingContent(
    dayTracking: DayTrackingType,
    onClick: (String, String, String) -> Unit,
) {
    val date: LocalDate = dayTracking.date
    val startTime: LocalTime? = dayTracking.startTime
    val endTime: LocalTime? = dayTracking.endTime
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

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(10))
            .clickable {
                val startTimeString = if (startTime!= null)  startTime.format(DateTimeFormatter.ofPattern("hh:mm")) else "-"
                val endTimeString = if (endTime!= null)  endTime.format(DateTimeFormatter.ofPattern("hh:mm")) else "-"
                onClick(
                    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    startTimeString,
                    endTimeString
                )
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = date.toString(),
                modifier = Modifier,
            )
            Text(
                text = "Durée : $formattedDuration",
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
        Row {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "forward arrow icon"
            )
            Text(text = "Heure d'embauche : ${startTime ?: " - "}")
        }
        Row {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "back arrow icon"
            )
            Text(text = "Heure de débauche : ${endTime ?: " - "}")
        }
    }
}
