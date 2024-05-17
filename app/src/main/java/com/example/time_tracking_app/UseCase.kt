package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.time_tracking_app.database.DayEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UseCase @Inject constructor(
    private val repository: DayRepository,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun initializeUserData() {
        repository.insertFirstDays()
    }

    fun addOrUpdateANewDayLocally(day: DayEntity) {
        repository.insertANewDay(day)
    }

    fun getAllUserDays(): Flow<List<DayEntity>> {
        return repository.getAllDays()
    }
}