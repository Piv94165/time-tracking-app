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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.R
import com.example.time_tracking_app.composables.weekPage.TimePickerDialog
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
        val startTime = remember {
            mutableStateOf(dayClicked?.startTime)
        }
        val writtenStartTime = remember {
            derivedStateOf{
                if (startTime.value == null) "" else convertors.convertTimeToString(
                    startTime.value!!
                )
            }
        }
        val endTime = remember {
            mutableStateOf(dayClicked?.endTime)
        }
        val writtenEndTime = remember {
            derivedStateOf {  if (endTime.value == null) "" else convertors.convertTimeToString(
                endTime.value!!
            ) }
        }
        val isErrorForStartTime = remember {
            mutableStateOf(false)
        }
        val isErrorForEndTime = remember {
            mutableStateOf(false)
        }
        val isStartTimePickerDialogOpen = remember {
            mutableStateOf(false)
        }
        val isEndTimePickerDialogOpen = remember {
            mutableStateOf(false)
        }

        TimePickerDialog(time = startTime, showDialog = isStartTimePickerDialogOpen)
        TimePickerDialog(time = endTime, showDialog = isEndTimePickerDialogOpen)
        Text(
            text = stringResource(
                id = R.string.validate_hours_page_title, "${
                    dayClicked?.date?.let {
                        convertors.convertDateToString(it)
                    }
                }"))
        TimeInput(
            writtenTime = writtenStartTime,
            label = stringResource(id = R.string.start_day_hour_label),
            isError = isErrorForStartTime,
            onClick = {
                isStartTimePickerDialogOpen.value = true
            }
        )
        TimeInput(
            writtenTime = writtenEndTime,
            label = stringResource(id = R.string.end_day_hour_label),
            isError = isErrorForEndTime,
            onClick = {
                isEndTimePickerDialogOpen.value = true
            }
        )
        Button(
            modifier = Modifier.padding(bottom = 4.dp),
            enabled = !isErrorForStartTime.value && !isErrorForEndTime.value,
            onClick = {
                timeEditSheetIsShown.value = false
                if (writtenStartTime.value !== "") {
                    dayClicked?.startTime =
                        convertors.convertStringToTime(writtenStartTime.value)
                }
                if (writtenEndTime.value !== "") {
                    dayClicked?.endTime =
                        convertors.convertStringToTime(writtenEndTime.value)
                }

                coroutineScope.launch(Dispatchers.IO) {
                    insertNewDay(dayClicked!!)
                }

            }) {
            Text(stringResource(id = R.string.validate_hours))
        }
    }
}