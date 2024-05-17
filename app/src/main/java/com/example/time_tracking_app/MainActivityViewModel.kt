package com.example.time_tracking_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.time_tracking_app.database.DayEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertFirstDays() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.initializeUserData()
        }
    }


    fun insertANewDay(
        newDay: DayEntity
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addOrUpdateANewDayLocally(newDay)
        }

    }

    fun getAllDays(): Flow<List<DayEntity>> {
        return useCase.getAllUserDays()
    }



}