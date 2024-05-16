package com.example.time_tracking_app.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.time_tracking_app.Convertors
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Converters {

    //val convertors = Convertors()
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimeStampToDate(timestamp: Long?): LocalDate? {
        //return value?.let { LocalDate.parse(it) }
        return timestamp?.let { LocalDate.ofEpochDay(timestamp/(60*60*24)) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromDateToTimestamp(date: LocalDate?): Long? {
        //return date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return date?.atStartOfDay()?.toEpochSecond(ZoneOffset.of("Z"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimeStampToTime(timestamp: Long?): LocalTime? {
        return timestamp?.let {
            val localDateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("Z"))
            localDateTime.toLocalTime()
        }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    @TypeConverter
    fun fromTimeToTimestamp(time: LocalTime?): Long? {
        //return date?.format(DateTimeFormatter.ofPattern("HH:mm"))
        //return time?.toEpochSecond(LocalDate.of(2000,1,1),ZoneOffset.of("Z"))
        //return date?.let { convertors.convertTimeToString(it) }
        return time?.let {
            val localDateTime = LocalDateTime.of(2000, 1, 1, it.hour, it.minute)
            localDateTime.toEpochSecond(ZoneOffset.of("Z"))
        }
    }
}