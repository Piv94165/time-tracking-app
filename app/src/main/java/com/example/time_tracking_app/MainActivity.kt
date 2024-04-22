package com.example.time_tracking_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import java.time.Duration
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
                    val currentDate = LocalDate.of(2024, 4, 22)
                    val startTime = LocalTime.of(9, 5)
                    val endTime = LocalTime.of(17, 34)
                    DayTracking(currentDate, startTime, endTime)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayTracking(date: LocalDate, startTime: LocalTime, endTime: LocalTime, modifier: Modifier = Modifier) {
    val duration = Duration.between(startTime, endTime)
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60

    val formattedDuration = "${hours}h${minutes}m"
    Surface (
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(12.dp),
        shape = RoundedCornerShape(10),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row (modifier = Modifier.fillMaxWidth()){
                Text(
                    text = date.toString(),
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "Durée : $formattedDuration",
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(8.dp)) // Ajouter un espacement vertical
            Text(text = "Heure d'embauche : $startTime")
            Spacer(modifier = Modifier.height(8.dp)) // Ajouter un espacement vertical
            Text(text = "Heure de débauche : $endTime")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimetrackingappTheme {
        val currentDate = LocalDate.of(2024, 4, 22)
        val startTime = LocalTime.of(9, 5)
        val endTime = LocalTime.of(17, 34)
        DayTracking(currentDate, startTime, endTime)
    }
}