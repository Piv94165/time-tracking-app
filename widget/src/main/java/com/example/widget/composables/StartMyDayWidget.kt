package com.example.widget.composables

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.semantics.contentDescription
import androidx.glance.semantics.semantics
import androidx.glance.text.Text
import com.example.datasource.database.DayEntity
import com.example.widget.R
import java.time.LocalTime

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun StartMyDayWidget(
    day: DayEntity,
    context: Context,
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
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = context.applicationContext.resources.getString( R.string.start_day_welcome_text),
            modifier = GlanceModifier
                .padding(bottom = 12.dp),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier.clickable {
            startDay()
        }.semantics {
            contentDescription = context.applicationContext.resources.getString(R.string.start_new_day)
        }
        ) {
            Image(provider = ImageProvider(R.drawable.start_day), contentDescription = null, modifier = GlanceModifier.size(50.dp).padding(8.dp))
            Text(text = context.applicationContext.resources.getString(R.string.start_new_day))
        }

    }

}