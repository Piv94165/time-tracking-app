package com.example.time_tracking_app.widget.composables

import android.content.Context
import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.semantics.contentDescription
import androidx.glance.semantics.semantics
import androidx.glance.text.Text
import com.example.time_tracking_app.R
import com.example.time_tracking_app.database.DayEntity
import java.time.LocalTime

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun EndMyDayWidget(
    day: DayEntity,
    context: Context,
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
        Text(
            text = context.applicationContext.resources.getString(R.string.you_start_your_day,day.startTime),
            modifier = GlanceModifier
                .padding(bottom = 12.dp)
        )
        Spacer(GlanceModifier.height(12.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier.clickable {
                endDay()
            }.semantics {
                contentDescription = context.applicationContext.resources.getString(R.string.start_new_day)
            }
        ) {
            Image(provider = ImageProvider(R.drawable.end_day), contentDescription = null, modifier = GlanceModifier.size(50.dp).padding(8.dp))
            Text(text = context.applicationContext.resources.getString(R.string.end_my_day))
        }

    }

    
}