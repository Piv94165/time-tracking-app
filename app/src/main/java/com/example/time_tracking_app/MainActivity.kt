package com.example.time_tracking_app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.time_tracking_app.bottomNavigation.BottomNavBar
import com.example.time_tracking_app.bottomNavigation.BottomNavBarPageContent
import com.example.time_tracking_app.bottomNavigation.NavigationItem
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint(
        "MutableCollectionMutableState",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainActivityViewModel = hiltViewModel<MainActivityViewModel>()
            mainActivityViewModel.sendToday()
            LaunchedEffect(key1 = Unit) {
                mainActivityViewModel.insertFirstDays()
            }

            TimetrackingappTheme {
                val navController = rememberNavController()

                val itemsList = listOf(
                    NavigationItem("today", "Aujourd'hui", Icons.Default.PlayArrow),
                    NavigationItem(
                        "week",
                        "Semaine",
                        Icons.Default.DateRange,
                    ),
                )

                val selectedItemBottomNavBar = remember {
                    mutableIntStateOf(0)
                }




                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            itemsList = itemsList,
                            selectedItem = selectedItemBottomNavBar,
                            navController = navController
                        )
                    },
                ) { paddingValues ->
                    BottomNavBarPageContent(
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}
