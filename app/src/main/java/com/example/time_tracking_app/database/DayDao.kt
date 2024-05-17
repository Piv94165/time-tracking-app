package com.example.time_tracking_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.time_tracking_app.DatabaseModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Query("SELECT * FROM DayEntity")
    fun getAll(): Flow<List<DayEntity>>

    @Query("SELECT * FROM DayEntity")
    fun getAllWithoutFlow(): List<DayEntity>

    @Query("SELECT * FROM DayEntity WHERE date IN (:dayDates)")
    fun loadAllByDates(dayDates: Array<String>): Flow<List<DayEntity>>

    @Query(
        "SELECT * FROM DayEntity WHERE date LIKE :date" +
            " LIMIT 1")
    fun findByDate(date: String): Flow<DayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDate(vararg dayEntity: DayEntity)

    @Delete
    suspend fun delete(dayEntity: DayEntity)
}