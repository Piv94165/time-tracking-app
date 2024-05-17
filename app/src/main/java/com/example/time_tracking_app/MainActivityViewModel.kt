package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.time_tracking_app.database.DayEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: UseCase,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertFirstDays() {
        useCase.initializeUserData()
    }


    fun insertANewDay(
        newDay: DayEntity
    ) {
        useCase.addOrUpdateANewDayLocally(newDay)

    }

    fun getAllDays(): Flow<List<DayEntity>> {
        return useCase.getAllUserDays()
    }



}