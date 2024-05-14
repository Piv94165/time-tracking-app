package com.example.time_tracking_app

import androidx.compose.runtime.Composable
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class DateFormatterTest {

    @Test
    fun conversionStringToTime() {
        val currentTime = LocalTime.parse("09:32")
        val expectedTime = LocalTime.of(9,32)
        Assert.assertEquals(expectedTime, currentTime)
    }

    @Test
    fun findDayInListOfDays() {
        val currentDate = LocalDate.now()
        val startTime = LocalTime.of(9, 5)
        val endTime = LocalTime.of(17, 34)
        val monday = DayTrackingType(currentDate.minusDays(2), startTime, endTime)
        val tuesday = DayTrackingType(currentDate.minusDays(1), startTime, endTime)
        val wednesday = DayTrackingType(currentDate, startTime)
        val thursday = DayTrackingType(currentDate.plusDays(1))
        val friday = DayTrackingType(currentDate.plusDays(2))

        val dayClicked :DayTrackingType = tuesday
        dayClicked.startTime = LocalTime.of(9,45)

        val listOfDays = mutableListOf(
            monday, tuesday, wednesday, thursday, friday,
        )

        val index = listOfDays.indexOfFirst { it.date == dayClicked.date }
        dayClicked.also {day ->
            listOfDays[index] = day
        }

        val expectedListOfDays = listOf(
            monday, dayClicked, wednesday, thursday, friday,
        )

        Assert.assertEquals(LocalTime.of(9,45), listOfDays[index].startTime)
    }
}