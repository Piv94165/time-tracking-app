package com.example.time_tracking_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PublicHolidayDao {
    @Query("SELECT * FROM PublicHolidayEntity")
    fun getAll(): List<PublicHolidayEntity>

    @Query(
        "SELECT * FROM PublicHolidayEntity WHERE date LIKE :date" +
                " LIMIT 1")
    fun findByDate(date: String): PublicHolidayEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePublicHoliday(vararg publicHolidayEntity: PublicHolidayEntity)

    @Delete
    suspend fun delete(publicHolidayEntity: PublicHolidayEntity)
}
