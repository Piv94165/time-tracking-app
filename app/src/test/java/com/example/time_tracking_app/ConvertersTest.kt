package com.example.time_tracking_app

import com.example.time_tracking_app.database.Converters
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

class ConvertersTest {

    private val converters = Convertors()
    private val convertersDb = Converters()
    @Test
    fun testStringToTimeFormat24h() {
        val resultatConversion = converters.convertStringToTime("14h56")
        Assert.assertEquals(14, resultatConversion.hour)
        Assert.assertEquals(56, resultatConversion.minute)
    }

    @Test
    fun testLongToTimeDb() {
        val resultatConversion = convertersDb.fromTimeStampToTime(946748040.toLong())
        val time = LocalTime.of(17, 34)
        Assert.assertEquals(time, resultatConversion)
    }

    @Test
    fun testLocalTimeToStringDb() {
        val time = LocalTime.of(17, 34)
        val resultatConversion = convertersDb.fromTimeToTimestamp(time)
        Assert.assertEquals(946748040.toLong(), resultatConversion)
    }

    @Test
    fun testTimeToLongDbIfTimeIsNull() {
        val resultatConversion = convertersDb.fromTimeStampToTime(null)
        Assert.assertEquals(null, resultatConversion)
    }

    @Test
    fun testStringToDateDb() {
        val date = LocalDate.of(2024,5,26)
        val timestamp = date.atStartOfDay()?.toEpochSecond(ZoneOffset.of("Z"))
        val resultatConversion = convertersDb.fromTimeStampToDate(timestamp)
        Assert.assertEquals(date, resultatConversion)
    }

    @Test
    fun testDateToStringDb() {
        val date = LocalDate.of(2024,5,26)
        val resultatConversion = convertersDb.fromDateToTimestamp(date)
        Assert.assertEquals(1716681600.toLong(), resultatConversion)
    }
}