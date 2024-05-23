package com.example.time_tracking_app.bottomNavigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.time_tracking_app.composables.todayPage.TodayPage
import com.example.time_tracking_app.composables.todayPage.TodayPageViewModel
import com.example.time_tracking_app.composables.weekPage.WeekPage
import com.example.time_tracking_app.composables.weekPage.WeekPageViewModel
import com.example.time_tracking_app.database.DayEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavBar(
    itemsList: List<NavigationItem>,
    selectedItem: MutableIntState,
    navController: NavHostController,
) {
    NavigationBar(
    ) {
        itemsList.forEachIndexed { index, item ->
            NavigationBarItem(selected = false,
                onClick = {
                    selectedItem.intValue = index
                    navController.navigate(item.key)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                }
            )

        }
    }

}