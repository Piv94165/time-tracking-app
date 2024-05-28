package com.example.time_tracking_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayDao {
    @Query("SELECT * FROM DayEntity")
    fun getAll(): Flow<List<DayEntity>>

    @Query("SELECT * FROM DayEntity")
    fun getAllWithoutFlow(): List<DayEntity>

    @Query("SELECT * FROM DayEntity WHERE date IN (:dayDates)")
    fun loadAllByDates(dayDates: Array<LocalDate>): Flow<List<DayEntity>>

    @Query("SELECT * FROM DayEntity WHERE date IN (:dayDates)")
    fun loadAllByDatesWithoutFlow(dayDates: Array<LocalDate>): List<DayEntity>

    @Query(
        "SELECT * FROM DayEntity WHERE date LIKE :date" +
            " LIMIT 1")
    fun findByDate(date: LocalDate): Flow<DayEntity>

    @Query(
        "SELECT * FROM DayEntity WHERE date LIKE :date" +
                " LIMIT 1")
    fun findByDateWithoutFlow(date: LocalDate): DayEntity?


    @Query(
        "SELECT * FROM DayEntity ORDER BY date DESC LIMIT 1"
    )
    fun findLastDate(): DayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDate(vararg dayEntity: DayEntity)

    @Delete
    suspend fun delete(dayEntity: DayEntity)
}