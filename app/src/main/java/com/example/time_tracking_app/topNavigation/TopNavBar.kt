package com.example.time_tracking_app.topNavigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopNavBar(
    weekNumber: Int,
    year: Int,
    onPreviousWeekClicked: () -> Unit,
    onNextWeekClicked: () -> Unit,

) {
    NavigationBar(
        modifier = Modifier.height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "semaine d'avant",
                modifier = Modifier.clickable { onPreviousWeekClicked() },
            )
            Text(text = "${year} - Semaine ${weekNumber}")
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "semaine d'après",
                modifier = Modifier.clickable { onNextWeekClicked() },
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewTopNavBar() {
    val selectedWeekNumber = remember {
        mutableIntStateOf(1)
    }
    val selectedYear = remember {
        mutableIntStateOf(2024)
    }
    TopNavBar(weekNumber = 1, year = 2024, onPreviousWeekClicked = {}, onNextWeekClicked = {})
}