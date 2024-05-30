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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import com.example.time_tracking_app.R
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
    val isEditable = dayEntityTracking.isEditable()

    val today = LocalDate.now()

    val backgroundColor = when {
        isEditable && date < today -> colorResource(id = R.color.previous_days) // previous days
        isEditable && date == today -> colorResource(id = R.color.today) // today
        else -> colorResource(id = R.color.disabled_days) // next days or public holidays
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(10))
            .padding(16.dp)
        .clickable(enabled = isEditable) {
                onClick()
            },

        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = convertors.convertDateToString(date),
                modifier = Modifier.testTag("day-card-date"),
            )
            Text(
                text = stringResource(
                    id = R.string.day_duration,
                    dayEntityTracking.stringDuration()
                ),
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
        Row {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
            )
            Text(
                text = stringResource(
                    id = R.string.start_day_hour,
                    if (startTime !== null)
                        convertors.convertTimeToString(startTime)
                    else " - "
                ),
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                )
                Text(
                    text = stringResource(
                        id = R.string.end_day_hour,
                        if (endTime !== null) convertors.convertTimeToString(
                            endTime
                        ) else " - "
                    ),
                )
            }
            if (dayEntityTracking.isPublicHoliday == true) {
                Row {
                    Text(text = stringResource(id = R.string.public_holiday))
                    Image(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(16.dp)
                            .semantics {
                                contentDescription = "jour férié"
                            },
                        painter = painterResource(id = R.drawable.public_holidays),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
