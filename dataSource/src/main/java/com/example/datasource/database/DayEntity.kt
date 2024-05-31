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
    fun stringDuration(): String {
        var formattedDuration = "-"

        if (startTime != null && endTime != null) {
            val duration = duration()
            val hours = duration.toHours()
            val minutes = (duration.toMinutes() % 60)
            val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
            formattedDuration = if (hours.toInt() ==0) "${minutes}min" else "${hours}h${minutesString}"
        }
        return formattedDuration
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun duration(): Duration {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime)
        }
        return Duration.ZERO
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isEditable(): Boolean {
        return date <= LocalDate.now() && this.isPublicHoliday == false
    }
}
