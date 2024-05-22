package com.example.time_tracking_app.database

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class DayEntity (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "date") val date:LocalDate,
    @ColumnInfo(name = "start_time") var startTime:LocalTime?,
    @ColumnInfo(name = "end_time") var endTime:LocalTime?,
    @ColumnInfo(name = "is_public_holiday") var isPublicHoliday:Boolean?,
) {
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    @Ignore
    constructor(date: LocalDate, startTime: LocalTime? = null, endTime: LocalTime? = null, isPublicHoliday: Boolean? = false) :
            this(
                "${date.year}${date.monthValue}${date.dayOfMonth}".toInt(),
                date,
                startTime,
                endTime,
                isPublicHoliday,
            )

    @RequiresApi(Build.VERSION_CODES.O)
    fun duration(): String {
        var formattedDuration = "-"

        if (startTime != null && endTime != null) {
            val duration = Duration.between(startTime, endTime)
            val hours = duration.toHours()
            val minutes = (duration.toMinutes() % 60)
            formattedDuration = "${hours}h${minutes}"
        }
        return formattedDuration
    }
}
