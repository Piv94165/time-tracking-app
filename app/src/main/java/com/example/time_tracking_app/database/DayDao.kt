package com.example.time_tracking_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Query("SELECT * FROM day")
    fun getAll(): Flow<List<Day>>

    @Query("SELECT * FROM Day WHERE date IN (:dayDates)")
    fun loadAllByDates(dayDates: Array<String>): Flow<List<Day>>

    @Query("SELECT * FROM Day WHERE date LIKE :date" +
            " LIMIT 1")
    fun findByDate(date: String): Flow<Day>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDate(vararg day: Day)

    @Delete
    suspend fun delete(day: Day)
}