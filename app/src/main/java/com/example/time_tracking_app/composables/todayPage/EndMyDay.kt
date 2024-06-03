package com.example.time_tracking_app.composables.todayPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.datasource.database.DayEntity
import com.example.time_tracking_app.R
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EndMyDay(
    day: DayEntity,
    editDay: (DayEntity) -> Unit,
) {

    fun endDay() {
        day.endTime = LocalTime.now()
        editDay(day)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .clickable { endDay() }
    ) {

        val endDayDescription = stringResource(id = R.string.end_my_day)
        Image(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .semantics {
                    contentDescription = endDayDescription
                },
            painter = painterResource(id = R.drawable.end_day),
            contentDescription = null
        )

        Text(
            text = stringResource(id = R.string.end_my_day),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}