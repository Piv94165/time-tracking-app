package com.example.time_tracking_app

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class DayEntityTest {

    @Test
    fun testDuration1h27min() {
        val dayEntity = DayEntity(
            date = LocalDate.now(),
            startTime = LocalTime.of(11, 0),
            endTime = LocalTime.of(12, 27),
        )

        Assert.assertEquals("1h27", dayEntity.stringDuration())
    }

    @Test
    fun testDurationEndDateBeforeStartDate() {
        val dayEntity = DayEntity(
            date = LocalDate.now(),
            startTime = LocalTime.of(15, 0),
            endTime = LocalTime.of(12, 27),
        )

        Assert.assertEquals("1h27", dayEntity.stringDuration())
    }
}