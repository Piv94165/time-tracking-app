package com.example.time_tracking_app.composables.weekPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.time_tracking_app.UseCase
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.utils.Convertors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class WeekPageViewModel @Inject constructor(
    private val useCase: UseCase,
    private val convertors: Convertors,
) : ViewModel() {

    private var rawSelectedWeek =
        LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
    private var rawSelectedYear = LocalDate.now().year

    private val _selectedWeek = MutableStateFlow(
        LocalDate.now().get(
            WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
        )
    )
    val selectedWeek : StateFlow<Int> = _selectedWeek
    private val _selectedYear = MutableStateFlow(2024)
    val selectedYear : StateFlow<Int> = _selectedYear

    fun allDaysForWeek() =
        useCase.getAllDaysOfSpecificWeek(selectedWeek.value, selectedYear.value)


    @RequiresApi(Build.VERSION_CODES.O)
    fun editDay(day: DayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addOrUpdateANewDayLocally(day)
        }
    }

    fun loadPreviousWeek() {
        if (rawSelectedWeek>1) {
            rawSelectedWeek -= 1
        } else {
            rawSelectedWeek = 52
            rawSelectedYear -=1
        }
        _selectedWeek.value = rawSelectedWeek
    }

    fun loadNextWeek() {
        if (rawSelectedWeek<52) {
            rawSelectedWeek += 1
        } else {
            rawSelectedWeek = 1
            rawSelectedYear +=1
        }
        _selectedWeek.value = rawSelectedWeek
    }

    fun providesConvertors() = convertors
}