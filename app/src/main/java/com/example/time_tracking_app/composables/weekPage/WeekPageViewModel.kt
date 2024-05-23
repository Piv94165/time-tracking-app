package com.example.time_tracking_app.composables.weekPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.time_tracking_app.UseCase
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.utils.Convertors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeekPageViewModel @Inject constructor(
    private val useCase: UseCase,
    private val convertors: Convertors,
) : ViewModel() {

    val allDays = useCase.allDays

    @RequiresApi(Build.VERSION_CODES.O)
    fun editDay(day: DayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addOrUpdateANewDayLocally(day)
        }
    }

    fun providesConvertors() = convertors
}