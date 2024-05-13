package com.example.time_tracking_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimetrackingappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentDate = LocalDate.of(2024, 5, 13)
                    val startTime = LocalTime.of(9, 5)
                    val endTime = LocalTime.of(17, 34)

                    Column(modifier = Modifier.padding(8.dp)) {
                        val listOfDays = listOf(
                            DayTrackingType(currentDate, startTime, endTime),
                            DayTrackingType(LocalDate.of(2024, 1, 13), startTime = startTime),
                            DayTrackingType(LocalDate.of(2024, 12, 24), endTime = endTime)
                        )

                        val listOfDaysInstance = ListOfDays(listOfDays)

                        listOfDaysInstance.Content()
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
        val currentDate = LocalDate.of(2024, 5, 13)
        val startTime = LocalTime.of(9, 5)
        val endTime = LocalTime.of(17, 34)

        Column(modifier = Modifier.padding(8.dp)) {
            val listOfDays = listOf(
                DayTrackingType(currentDate, startTime, endTime),
                DayTrackingType(LocalDate.of(2024, 5, 7), startTime = startTime),
                DayTrackingType(LocalDate.of(2024, 9, 10), endTime = endTime)
            )

            val listOfDaysInstance = ListOfDays(listOfDays)

            listOfDaysInstance.Content()
        }
    }
}