package com.example.time_tracking_app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.time_tracking_app.database.AppDatabase
import com.example.time_tracking_app.database.Day
import com.example.time_tracking_app.database.DayDao
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    @SuppressLint("MutableCollectionMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimetrackingappTheme {
                val convertors = Convertors()

                val db = Room
                    .databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "days-database"
                    )
                    //.createFromAsset("")
                    .build()

                val dayDao = db.dayDao()

                val currentDate = LocalDate.now()
                val startTime = LocalTime.of(9, 5)
                val endTime = LocalTime.of(17, 34)

                val monday =
                    Day(date = currentDate.minusDays(2), startTime = startTime, endTime = endTime)
                val tuesday = Day(date = currentDate.minusDays(1), startTime, endTime)
                val wednesday = Day(date = currentDate, startTime = startTime)
                val thursday = Day(date = currentDate.plusDays(1))
                val friday = Day(date = currentDate.plusDays(2))
                val initialListOfDays = listOf(monday, tuesday, wednesday, thursday, friday)

                val coroutineScope = rememberCoroutineScope()
                val listOfDays = dayDao.getAll().collectAsState(initial = emptyList())

                suspend fun insertFirstData(): Unit {
                    initialListOfDays.forEach { day -> dayDao.insertOrUpdateDate(day) }
                }

                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch(Dispatchers.IO) {
                        insertFirstData()
                    }
                }

                val dayClicked = remember {
                    mutableStateOf<Day?>(null)
                }

                suspend fun insertNewDay() {
                    dayClicked.value?.let { dayDao.insertOrUpdateDate(it) }
                }


                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch(Dispatchers.IO) {
                        insertNewDay()
                    }
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
                                mutableStateOf(if (dayClicked.value?.startTime == null) "" else convertors.convertTimeToString(
                                    dayClicked.value?.startTime!!
                                ))
                            }
                            val writtenEndDate = remember {
                                mutableStateOf(
                                    if (dayClicked.value?.endTime == null) "" else convertors.convertTimeToString(
                                        dayClicked.value?.endTime!!)
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
                                    if (writtenStartDate.value !== ""){
                                        dayClicked.value?.startTime =
                                            convertors.convertStringToTime(writtenStartDate.value)
                                    }
                                    if (writtenEndDate.value !== "") {
                                        dayClicked.value?.endTime =
                                            convertors.convertStringToTime(writtenEndDate.value)
                                    }

                                    coroutineScope.launch(Dispatchers.IO) {
                                        insertNewDay()
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
                        items(listOfDays.value) { day ->
                            val dayTrackingType = day?.date?.let {
                                Day(
                                    it,
                                    day.startTime,
                                    day.endTime
                                )
                            }
                            if (dayTrackingType != null) {
                                DayTrackingContent(
                                    dayTrackingType
                                ) { //date, startTime, endTime ->
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