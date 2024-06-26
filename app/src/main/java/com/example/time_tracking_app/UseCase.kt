package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.time_tracking_app.database.Converters
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.database.PublicHolidayEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import java.time.Duration

class UseCase @Inject constructor(
    private val repository: DayRepository,
) {
    private val converters = Converters()

    val allDays = repository.allDays
    fun allDaysAsFun() = repository.allDaysAsFun()

    private fun getDaysByDates(dates: Array<LocalDate>) = repository.getDaysByDates(dates)

    private fun getDaysByDatesWithoutFlow(dates: Array<LocalDate>) = repository.getDaysByDatesWithoutFlow(dates)
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayByDate (date:LocalDate) = repository.getDayByDate(date)


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun initializeUserData() {
        getPublicHolidaysFromAPIAndInsertInDb()

        val currentDate = LocalDate.now()
        val week = completeWeekOfADay(currentDate)
        val lastStoredDate = repository.getLastDayStoredInDb()

        for (day in week) {
                if (lastStoredDate==null || (day > lastStoredDate.date)) {
                    addOrUpdateANewDayLocally(DayEntity(date = day))
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getPublicHolidaysFromAPIAndInsertInDb() {
        val publicHolidays = repository.getPublicHolidayFromRemote()
        publicHolidays.body()?.forEach { (date: String, name: String) ->
            val publicHoliday = PublicHolidayEntity(converters.convertStringApiDateToLocalDate(date), name)
            repository.insertOrUpdatePublicHoliday(publicHoliday)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPublicHolidaysFromDb(year: Int = LocalDate.now().year): List<PublicHolidayEntity> {
        if (!repository.hasPublicHolidayForYear(year)) {
            getPublicHolidaysFromAPIAndInsertInDb()
        }
        return repository.getPublicHolidayFromDb()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addOrUpdateANewDayLocally(day: DayEntity) {
        val publicHolidays = getPublicHolidaysFromDb()
        if (publicHolidays.any { publicHoliday -> publicHoliday.date == day.date }) {
            day.isPublicHoliday = true
        }
        repository.insertANewDay(day)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createDayEntityForLocalDateIfDoesNotExist(localDate: LocalDate) {
        val dayFromDb = repository.getDayByDateWithoutFlow(localDate)
        if (dayFromDb == null) {
            addOrUpdateANewDayLocally(DayEntity(date = localDate))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun completeWeekOfADay(date: LocalDate): List<LocalDate> {
        val dayOfWeek = date.dayOfWeek.value
        val week = Array<LocalDate?>(5) {null}
        for (dayInt in 1..5) {
            if (dayInt<=dayOfWeek) {
                week[dayInt-1] = date.minusDays((dayOfWeek-dayInt).toLong())
            } else {
                week[dayInt-1] = date.plusDays((dayInt-dayOfWeek).toLong())
            }
        }
        return week.filterNotNull()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllDaysOfSpecificWeek(week: Int, year: Int): Flow<List<DayEntity>> {
        val mondayOfTheWeek = LocalDate.ofYearDay(year, (week - 1) * 7 + 1)
        val daysStoredInDb = getDaysByDatesWithoutFlow(Array(5) { mondayOfTheWeek.plusDays(it.toLong()) })

        val daysCount = daysStoredInDb.size
        // Add missing days (first days of the week)
        for (i in daysCount..4) {
            addOrUpdateANewDayLocally(DayEntity(mondayOfTheWeek.plusDays((4-i).toLong())))
        }
        return getDaysByDates(Array(5) { mondayOfTheWeek.plusDays(it.toLong()) })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorkingHours(weekDays: List<DayEntity>): Duration {
        var hours = Duration.ZERO
        weekDays.forEach {day ->
            val dur = day.duration()
            hours = hours.plus(dur)
        }
        return hours
    }

}