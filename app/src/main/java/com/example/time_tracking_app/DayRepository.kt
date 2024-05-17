package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.time_tracking_app.database.DayDao
import com.example.time_tracking_app.database.DayEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class DayRepository(
    private val dao: DayDao,
) {

    @RequiresApi(Build.VERSION_CODES.O)

    fun insertFirstDays() {
        val currentDate = LocalDate.now()
        val startTime = LocalTime.of(9, 5)
        val endTime = LocalTime.of(17, 34)

        val monday =
            DayEntity(
                date = currentDate.minusDays(2),
                startTime = startTime,
                endTime = endTime
            )
        val tuesday = DayEntity(date = currentDate.minusDays(1), startTime, endTime)
        val wednesday = DayEntity(date = currentDate, startTime = startTime)
        val thursday = DayEntity(date = currentDate.plusDays(1))
        val friday = DayEntity(date = currentDate.plusDays(2))
        val saturday = DayEntity(date = currentDate.plusDays(3))
        val initialListOfDays =
            listOf(monday, tuesday, wednesday, thursday, friday, saturday)

        initialListOfDays.forEach { day -> insertANewDay(day) }

    }

    fun insertANewDay(
        newDay: DayEntity
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            newDay.let { dao.insertOrUpdateDate(it) }
        }

    }

    fun getAllDays(): Flow<List<DayEntity>> {
        return dao.getAll()
    }
}