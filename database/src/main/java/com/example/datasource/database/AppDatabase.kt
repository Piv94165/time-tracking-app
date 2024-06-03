package com.example.datasource.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.time_tracking_app.database.DayDao

@Database(
    entities = [DayEntity::class, PublicHolidayEntity::class],
    version = 1,
    //autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun publicHolidayDao(): PublicHolidayDao

}