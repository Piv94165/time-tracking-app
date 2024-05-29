package com.example.time_tracking_app.widget.composables

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.room.util.TableInfo
import com.example.time_tracking_app.R
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.utils.Convertors

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodaySummaryWidget(
    day: DayEntity,
    context: Context,
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
        Text(text = context.applicationContext.resources.getString(R.string.start_day_hour,
            day.startTime?.let { convertors.convertTimeToString(it) }))
        Text(text = context.applicationContext.resources.getString(R.string.end_day_hour,
            day.endTime?.let { convertors.convertTimeToString(it) }))
        Text(text = context.applicationContext.resources.getString(R.string.working_hours, day.stringDuration()))
        Spacer(modifier = GlanceModifier.height(12.dp))
        Text(text = context.applicationContext.resources.getString(R.string.end_of_day_message))
    }


}