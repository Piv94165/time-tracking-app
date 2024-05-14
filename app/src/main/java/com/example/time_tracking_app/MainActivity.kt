package com.example.time_tracking_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimetrackingappTheme {
                val timeEditSheetIsShown = remember{ mutableStateOf(false)}

                val modalBottomSheetState = rememberModalBottomSheetState()

                val startTimeInput = remember { mutableStateOf("startTime")}
                val endTimeInput = remember { mutableStateOf("endTime")}
                val editDate = remember { mutableStateOf(LocalDate.now().toString())}

                if (timeEditSheetIsShown.value) {
                    ModalBottomSheet(
                        onDismissRequest = { timeEditSheetIsShown.value = false },
                        sheetState = modalBottomSheetState,
                        dragHandle = { BottomSheetDefaults.DragHandle() },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Horaires du ${editDate.value}")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = if (startTimeInput.value=="null") "" else startTimeInput.value,
                                onValueChange = { startTimeInput.value = it },
                                label = { Text("Heure d'embauche")},
                                singleLine = true,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = if (endTimeInput.value=="null") "" else endTimeInput.value,
                                onValueChange = { endTimeInput.value = it },
                                label = { Text("Heure de débauche")},
                                singleLine = true,
                                )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                modifier = Modifier.padding(bottom = 4.dp),
                                onClick = { timeEditSheetIsShown.value = false }
                            ) {
                                Text("Valider les horaires")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val currentDate = LocalDate.now()
                    val startTime = LocalTime.of(9, 5)
                    val endTime = LocalTime.of(17, 34)

                    Column(modifier = Modifier.padding(8.dp)) {
                        val listOfDays = listOf(
                            DayTrackingType(currentDate, startTime, endTime),
                            DayTrackingType(LocalDate.of(2024, 1, 13), startTime = startTime),
                            DayTrackingType(LocalDate.of(2024, 12, 24), endTime = endTime)
                        )

                        Content(listOfDays, {timeEditSheetIsShown.value = true}, editDate, startTimeInput, endTimeInput)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimetrackingappTheme {
        val currentDate = LocalDate.now()
        val startTime = LocalTime.of(9, 5)
        val endTime = LocalTime.of(17, 34)

        Column(modifier = Modifier.padding(8.dp)) {
            val listOfDays = listOf(
                DayTrackingType(currentDate, startTime, endTime),
                DayTrackingType(LocalDate.of(2024, 5, 7), startTime = startTime),
                DayTrackingType(LocalDate.of(2024, 9, 10), endTime = endTime)
            )


            //Content(listOfDays){}
        }
    }
}