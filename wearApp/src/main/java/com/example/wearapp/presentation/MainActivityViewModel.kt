package com.example.wearapp.presentation

import androidx.lifecycle.ViewModel
import com.example.repository.DayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val repository: DayRepository
) : ViewModel() {
    val day = repository.allDays.map {
        it.firstOrNull()
    }
}