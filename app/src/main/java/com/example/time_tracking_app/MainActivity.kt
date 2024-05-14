package com.example.time_tracking_app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    @SuppressLint("MutableCollectionMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimetrackingappTheme {

                val currentDate = LocalDate.now()
                val startTime = LocalTime.of(9, 5)
                val endTime = LocalTime.of(17, 34)
                val monday = DayTrackingType(currentDate.minusDays(2), startTime, endTime)
                val tuesday = DayTrackingType(currentDate.minusDays(1), startTime, endTime)
                val wednesday = DayTrackingType(currentDate, startTime)
                val thursday = DayTrackingType(currentDate.plusDays(1))
                val friday = DayTrackingType(currentDate.plusDays(2))

                val dayClicked = remember {
                    mutableStateOf<DayTrackingType?>(null)
                }

                val listOfDays = remember {
                    mutableStateListOf(
                            monday, tuesday, wednesday, thursday, friday,
                    )
                }

                val timeEditSheetIsShown = remember { mutableStateOf(false) }

                val modalBottomSheetState = rememberModalBottomSheetState()

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
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            val writtenStartDate = remember {
                                mutableStateOf(if (dayClicked.value?.startTime == null) "" else dayClicked.value?.startTime.toString())
                            }
                            val writtenEndDate = remember {
                                mutableStateOf(
                                    if (dayClicked.value?.endTime == null) "" else dayClicked.value?.endTime?.toString()
                                        .orEmpty()
                                )
                            }
                            Text(text = "Horaires du ${dayClicked.value?.date}")
                            OutlinedTextField(
                                value = writtenStartDate.value,
                                onValueChange = { writtenStartDate.value = it },
                                label = { Text("Heure d'embauche") },
                                singleLine = true,
                            )
                            OutlinedTextField(
                                value = writtenEndDate.value,
                                onValueChange = { writtenEndDate.value = it },
                                label = { Text("Heure de débauche") },
                                singleLine = true,
                            )
                            Button(
                                modifier = Modifier.padding(bottom = 4.dp),
                                onClick = {
                                    timeEditSheetIsShown.value = false
                                    //val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")
                                    dayClicked.value?.startTime =
                                        LocalTime.parse(writtenStartDate.value)
                                    dayClicked.value?.endTime =
                                        LocalTime.parse(writtenEndDate.value)
                                    val index =
                                        listOfDays.indexOfFirst { it.date == dayClicked.value?.date }

                                    dayClicked.value?.also { day ->
                                        listOfDays.removeAt(index)
                                        listOfDays.add(index,day)
                                    }

                                }
                            ) {
                                Text("Valider les horaires")
                            }
                        }

                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LazyColumn(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(listOfDays) { day ->
                            val dayTrackingType = DayTrackingType(
                                day.date,
                                day.startTime,
                                day.endTime
                            )
                            DayTrackingContent(
                                dayTrackingType
                            ) { date, startTime, endTime ->
                                timeEditSheetIsShown.value = true
                                dayClicked.value = dayTrackingType
                            }
                        }
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