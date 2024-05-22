package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.time_tracking_app.database.DayDao
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.database.PublicHolidayDao
import com.example.time_tracking_app.database.PublicHolidayEntity
import com.example.time_tracking_app.network.PublicHolidaysService
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class DayRepository @Inject constructor(
    private val dayDao: DayDao,
    private val publicHolidayDao: PublicHolidayDao,
    private val publicHolidayService: PublicHolidaysService,
) {
    @RequiresApi(Build.VERSION_CODES.O)

    suspend fun insertFirstDays() {
        val days = getAllDaysWithoutFlow()
        if (days.isEmpty()) {
            val currentDate = LocalDate.now()
            val startTime = LocalTime.of(9, 5)
            val endTime = LocalTime.of(17, 34)

            val monday = DayEntity(
                date = currentDate.minusDays(2), startTime = startTime, endTime = endTime
            )
            val tuesday = DayEntity(date = currentDate.minusDays(1), startTime, endTime)
            val wednesday = DayEntity(date = currentDate, startTime = startTime)
            val thursday = DayEntity(date = currentDate.plusDays(1))
            val friday = DayEntity(date = currentDate.plusDays(2))
            val saturday = DayEntity(date = currentDate.plusDays(3))
            val initialListOfDays = listOf(monday, tuesday, wednesday, thursday, friday, saturday)
            val publicHolidays =
                getPublicHolidayFromDb().filter { it -> it.date.year == currentDate.year }
            initialListOfDays.forEach { day ->
                if (publicHolidays.any {
                        it.date == day.date
                    }) day.isPublicHoliday = true
                insertANewDay(day)
            }
        }

    }

    suspend fun insertANewDay(newDay: DayEntity) {
        dayDao.insertOrUpdateDate(newDay)
    }

    val allDays = dayDao.getAll()

    private fun getAllDaysWithoutFlow() = dayDao.getAllWithoutFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPublicHolidayFromRemote() =
        publicHolidayService.getPublicHolidaysForAZoneForAYear(LocalDate.now().year)

    suspend fun getPublicHolidayFromDb() = publicHolidayDao.getAll()

    suspend fun insertOrUpdatePublicHoliday(publicHoliday: PublicHolidayEntity) {
        publicHolidayDao.insertOrUpdatePublicHoliday(publicHoliday)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun hasPublicHolidayForYear(year: Int) =
        getPublicHolidayFromDb().any { it.date.year == year }

    suspend fun getLastDayStoredInDb() = dayDao.findLastDate()
}