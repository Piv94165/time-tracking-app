package com.example.time_tracking_app
import completeWeekOfADay
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class CompleteWeekOfADayTest {

    @Test
    fun testCompleteWeek() {
        val date = LocalDate.of(2024,5,22)
        val result = completeWeekOfADay(date)

        val startDate = LocalDate.of(2024, 5, 20)
        val expectedArray: Array<LocalDate?> = Array(5) { index ->
            startDate.plusDays(index.toLong())
        }

        Assert.assertTrue(expectedArray contentEquals result)
    }
}