package com.example.time_tracking_app.composables.weekPage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import com.example.time_tracking_app.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    time: MutableState<LocalTime?>,
    showDialog: MutableState<Boolean>,
) {
    val selectedHour by remember {
        time.let {time ->
            time.value?.let { mutableIntStateOf(it.hour) }
        } ?: mutableIntStateOf(0)
    }
    val selectedMinute by remember {
        time.let { time ->
            time.value?.let { mutableIntStateOf(it.minute) }
        } ?: mutableIntStateOf(0)
    }
    val timeState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier.fillMaxWidth().background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.LightGray.copy(alpha = .3f))
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timeState, layoutType = TimePickerLayoutType.Vertical)
                TextButton(onClick = {
                    time.value = LocalTime.of(timeState.hour,timeState.minute)
                    showDialog.value = false
                },
                    modifier = Modifier.align(Alignment.End)) {
                    Text(text = stringResource(id = R.string.ok_button))
                }
            }

        }
    }
}
