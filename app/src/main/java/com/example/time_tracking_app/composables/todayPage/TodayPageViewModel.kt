package com.example.time_tracking_app.composables.todayPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.time_tracking_app.UseCase
import com.example.time_tracking_app.database.DayEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayPageViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDay = useCase.getDayByDate(LocalDate.now())

   @RequiresApi(Build.VERSION_CODES.O)
    fun editDay(day: DayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addOrUpdateANewDayLocally(day)
        }
    }



}