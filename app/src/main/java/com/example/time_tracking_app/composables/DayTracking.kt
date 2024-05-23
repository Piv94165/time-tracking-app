package com.example.time_tracking_app.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.utils.Convertors
import com.example.time_tracking_app.database.DayEntity
import java.time.LocalDate
import java.time.LocalTime

data class DayTrackingType(
    val date: LocalDate,
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayTrackingContent(
    dayEntityTracking: DayEntity,
    convertors: Convertors,
    onClick: () -> Unit,
) {
    val date: LocalDate = dayEntityTracking.date
    val startTime: LocalTime? = dayEntityTracking.startTime
    val endTime: LocalTime? = dayEntityTracking.endTime

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
                onClick()
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = convertors.convertDateToString(date),
                modifier = Modifier,
            )
            Text(
                text = "Durée : ${dayEntityTracking.duration()}",
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
        Row {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
            )
            Text(
                text = "Heure d'embauche : ${
                    if (startTime !== null) convertors.convertTimeToString(
                        startTime
                    ) else " - "
                }"
            )
        }
        Row {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
            )
            Text(
                text = "Heure de débauche : ${
                    if (endTime !== null) convertors.convertTimeToString(
                        endTime
                    ) else " - "
                }"
            )
        }
        if (dayEntityTracking.isPublicHoliday == true) {

            Row {
                Text(text = "ceci est un jour férié")
            }
        }
    }
}
