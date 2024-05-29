package com.example.time_tracking_app.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

@Composable
fun TimeInput(
    writtenTime: State<String>,
    label: String,
    isError: MutableState<Boolean>,
    onClick: () -> Unit,
) {

    OutlinedTextField(
        interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick()
                        }
                    }
                }
            },
        value = writtenTime.value,
        onValueChange = {},
        isError = isError.value,
        label = { Text(label) },
        supportingText = { Text("ex: 08h12") },
        singleLine = true,
    )
}


