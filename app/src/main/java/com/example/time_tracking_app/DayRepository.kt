package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.time_tracking_app.database.Converters
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
    private val converters = Converters()

    val allDays = dayDao.getAll()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayByDate(date:LocalDate) = dayDao.findByDate(
        converters.fromDateToTimestamp(date).toString()
    )

    suspend fun insertANewDay(newDay: DayEntity) {
        dayDao.insertOrUpdateDate(newDay)
    }

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