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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
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


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DayTrackingContent(
        dayTracking: DayTrackingType,
        onClick: () -> Unit,
        date2: MutableState<String>,
        startTime2: MutableState<String>,
        endTime2: MutableState<String>,
    ) {
         val date: LocalDate = dayTracking.date
         val startTime: LocalTime? = dayTracking.startTime
         val endTime: LocalTime? = dayTracking.endTime
         val modifier: Modifier = dayTracking.modifier
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
            onClick = {
                onClick()
                date2.value = date.toString()
                startTime2.value = startTime.toString()
                endTime2.value = endTime.toString()
            }
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
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "forward arrow icon"
                    )
                    Text(text = "Heure d'embauche : ${startTime ?: " - "}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow icon"
                    )
                    Text(text = "Heure de débauche : ${endTime ?: " - "}")
                }
            }
        }
    }
