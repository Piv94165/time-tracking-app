package com.example.time_tracking_app

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datasource.database.DayEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: UseCase,
    private val sendDayUseCase: SendDayToWearUsecase,
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertFirstDays() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.initializeUserData()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun insertANewDay(
        newDay: DayEntity
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addOrUpdateANewDayLocally(newDay)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPublicHolidays() {
            try {
                val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
                    throwable.printStackTrace()
                }
                viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                    useCase.getPublicHolidaysFromDb()
                }
            }
            catch (e: Exception) {
                Log.d("ERROR",e.message.orEmpty())
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendToday() {
        viewModelScope.launch {
            useCase.getDayByDate(LocalDate.now()).collect {
                sendDayUseCase.sendDay(it)

            }
        }
    }

}