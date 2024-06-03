package com.example.time_tracking_app.composables.todayPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.datasource.database.DayEntity
import com.example.time_tracking_app.utils.Convertors
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayPage(
    day: DayEntity,
    editDay: (DayEntity) -> Unit,
    convertors: Convertors,
) {
    if (day.startTime == null) {
        StartMyDay(editDay)
    } else if (day.endTime == null) {
        EndMyDay(day,editDay)
    } else {
        TodaySummary(day,convertors)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewStartMyDayPage() {
    TodayPage(
        day = DayEntity(LocalDate.of(2024,5,23)),
        editDay = { _ -> },
        convertors = Convertors()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewEndMyDayPage() {
    TodayPage(
        day = DayEntity(LocalDate.of(2024,5,23),LocalTime.now()),
        editDay = { _ -> },
        convertors = Convertors()
    )
}