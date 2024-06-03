package com.example.datasource.database

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class PublicHolidayEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "name") val name: String,
) {
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    @Ignore
    constructor(date: LocalDate, name: String) :
            this(
                "${date.year}${date.monthValue}${date.dayOfMonth}".toInt(),
                date,
                name
            )
}
