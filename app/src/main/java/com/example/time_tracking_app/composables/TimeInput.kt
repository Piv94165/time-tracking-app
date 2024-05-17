package com.example.time_tracking_app.composables

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun TimeInput(
    writtenTime : MutableState<String>,
    label: String,
    isError: MutableState<Boolean>,
) {

    OutlinedTextField(
        value = writtenTime.value,
        onValueChange = {
            writtenTime.value = it
            if (isTimeFormatCorrect(it)) {
                isError.value = false
            } else {
                isError.value = true
            }
        },
        isError = isError.value,
        label = { Text(label) },
        supportingText = { Text("ex: 08h12")},
        singleLine = true,
    )
}

fun isTimeFormatCorrect(time:String): Boolean {
    return time.matches(Regex("^([01][0-9]|2[0-3])h[0-5][0-9]$"))
}

