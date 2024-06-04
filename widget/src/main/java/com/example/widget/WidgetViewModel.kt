package com.example.widget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datasource.database.DayEntity
import com.example.time_tracking_app.database.DayDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

//@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class WidgetViewModel @Inject constructor(
    private val dayDao: DayDao,
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayEntity(): StateFlow<DayEntity> {
        val _todayEntity = MutableStateFlow(DayEntity(LocalDate.now()))
        val todayEntity: StateFlow<DayEntity> = _todayEntity
        viewModelScope.launch(Dispatchers.IO) {
            dayDao.findByDate(LocalDate.now()).collect{_todayEntity.value = it}
        }
        return todayEntity
    }
}