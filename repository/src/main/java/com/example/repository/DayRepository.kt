package com.example.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.datasource.database.DayEntity
import com.example.datasource.database.PublicHolidayDao
import com.example.datasource.database.PublicHolidayEntity
import com.example.time_tracking_app.database.DayDao
import com.example.webservice.PublicHolidaysService
import java.time.LocalDate
import javax.inject.Inject

class DayRepository @Inject constructor(
    private val dayDao: DayDao,
    private val publicHolidayDao: PublicHolidayDao,
    private val publicHolidayService: PublicHolidaysService,
) {

    val allDays = dayDao.getAll()
    fun allDaysAsFun() = dayDao.getAll()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayByDate(date:LocalDate) = dayDao.findByDate(date)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayByDateWithoutFlow(date:LocalDate) = dayDao.findByDateWithoutFlow(date)

    fun getDaysByDates(dates: Array<LocalDate>) = dayDao.loadAllByDates(dates)

    fun getDaysByDatesWithoutFlow(dates: Array<LocalDate>) = dayDao.loadAllByDatesWithoutFlow(dates)

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