package com.example.time_tracking_app.composables.weekPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.time_tracking_app.UseCase
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.utils.Convertors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
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
    val selectedWeek: StateFlow<Int> = _selectedWeek
    private val _selectedYear = MutableStateFlow(2024)
    val selectedYear: StateFlow<Int> = _selectedYear

    val _workingHours = MutableStateFlow("")
    val workingHoursDuration = MutableStateFlow<String>("")

    fun allDaysForWeek(): StateFlow<List<DayEntity>> {
        val _result = MutableStateFlow<List<DayEntity>>(emptyList())
        val result: StateFlow<List<DayEntity>> = _result
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllDaysOfSpecificWeek(selectedWeek.value, selectedYear.value)
                .collect { days ->
                    _result.value = days
                    workingHoursDuration.value = convertors.convertDurationToString(
                        days
                            .map { it.duration() }
                            .reduce { sum, duration -> sum.plus(duration) }
                    )
                }
        }
        return result
    }

    fun getWorkingHours(weekDays: List<DayEntity>): String {
        return convertors.convertDurationToString(useCase.getWorkingHours(weekDays))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editDay(day: DayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addOrUpdateANewDayLocally(day)
        }
    }

    fun loadPreviousWeek() {
        if (rawSelectedWeek > 1) {
            rawSelectedWeek -= 1
        } else {
            rawSelectedWeek = 52
            rawSelectedYear -= 1
        }
        _selectedWeek.value = rawSelectedWeek
    }

    fun loadNextWeek() {
        println("hello from loadNextWeek selectedWeek = ${selectedWeek.value}")
        if (rawSelectedWeek < 52) {
            rawSelectedWeek += 1
        } else {
            rawSelectedWeek = 1
            rawSelectedYear += 1
        }
        _selectedWeek.value = rawSelectedWeek
        println("hello from loadNextWeek 2 selectedWeek = ${selectedWeek.value}")
    }

    fun providesConvertors() = convertors

    fun updateSelectedWeekForTestUI(week: Int, year: Int) {
        if (week < 52 && week >0) {
            _selectedWeek.value = week
            rawSelectedWeek = week
            rawSelectedYear = year
            _selectedYear.value = year
            allDaysForWeek()
        }
    }
}