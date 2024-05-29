package com.example.time_tracking_app.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.R
import com.example.time_tracking_app.utils.Convertors
import com.example.time_tracking_app.database.DayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditDay(
    dayClicked: DayEntity?,
    timeEditSheetIsShown: MutableState<Boolean>,
    convertors: Convertors,
    insertNewDay: suspend (DayEntity) -> Unit,

    ) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val writtenStartDate = remember {
            mutableStateOf(
                if (dayClicked?.startTime == null) "" else convertors.convertTimeToString(
                    dayClicked.startTime!!
                )
            )
        }
        val writtenEndDate = remember {
            mutableStateOf(
                if (dayClicked?.endTime == null) "" else convertors.convertTimeToString(
                    dayClicked.endTime!!
                )
            )
        }
        val isErrorForStartTime = remember {
            mutableStateOf(false)
        }
        val isErrorForEndTime = remember {
            mutableStateOf(false)
        }
        Text(text = stringResource(id = R.string.validate_hours_page_title, "${ dayClicked?.date?.let {
            convertors.convertDateToString(it)
        } }"))
        TimeInput(writtenTime = writtenStartDate, label = stringResource(id = R.string.start_day_hour_label), isErrorForStartTime)
        TimeInput(writtenTime = writtenEndDate, label = stringResource(id = R.string.end_day_hour_label), isErrorForEndTime)
        Button(modifier = Modifier.padding(bottom = 4.dp), enabled = !isErrorForStartTime.value&&!isErrorForEndTime.value, onClick = {
            timeEditSheetIsShown.value = false
            if (writtenStartDate.value !== "") {
                dayClicked?.startTime =
                    convertors.convertStringToTime(writtenStartDate.value)
            }
            if (writtenEndDate.value !== "") {
                dayClicked?.endTime =
                    convertors.convertStringToTime(writtenEndDate.value)
            }

            coroutineScope.launch(Dispatchers.IO) {
                insertNewDay(dayClicked!!)
            }

        }) {
            Text(stringResource(id = R.string.validate_hours))
        }
    }
}