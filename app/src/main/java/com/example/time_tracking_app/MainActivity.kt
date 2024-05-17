package com.example.time_tracking_app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.time_tracking_app.bottomNavigation.NavigationItem
import com.example.time_tracking_app.composables.EditDay
import com.example.time_tracking_app.composables.WeekPage
import com.example.time_tracking_app.database.DayEntity
import com.example.time_tracking_app.ui.theme.TimetrackingappTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var mainActivityViewModel: MainActivityViewModel
    @SuppressLint("MutableCollectionMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        val db = Database.getInstance(applicationContext)
        val dao = db.dayDao()
        val repository = DayRepository(dao)
        val useCase = UseCase(repository)
        val mainActivityViewModel = MainActivityViewModel(useCase)
         */
        mainActivityViewModel.insertFirstDays()

        setContent {
            TimetrackingappTheme {
                val listOfDays = mainActivityViewModel.getAllDays().collectAsState(initial = emptyList())
                val dayEntityClicked = remember {
                    mutableStateOf<DayEntity?>(null)
                }
                val timeEditSheetIsShown = remember { mutableStateOf(false) }

                val modalBottomSheetState = rememberModalBottomSheetState()

                if (timeEditSheetIsShown.value) {
                    ModalBottomSheet(
                        onDismissRequest = { timeEditSheetIsShown.value = false },
                        sheetState = modalBottomSheetState,
                        dragHandle = { BottomSheetDefaults.DragHandle() },
                    ) {
                        EditDay(
                            dayClicked = dayEntityClicked.value,
                            timeEditSheetIsShown = timeEditSheetIsShown,
                            insertNewDay = { day ->
                                Log.d("", "Hello from insert new day $day")
                                mainActivityViewModel.insertANewDay(newDay = day)
                            },
                        )
                    }
                }

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
                    mutableIntStateOf(1)
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                        ) {
                            itemsList.forEachIndexed { index, item ->
                                NavigationBarItem(selected = selectedItemBottomNavBar.intValue == index,
                                    onClick = {
                                        selectedItemBottomNavBar.intValue = index
                                        navController.navigate(item.key)
                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon, contentDescription = item.title
                                        )
                                    })
                            }
                        }
                    }) { paddingValues ->
                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = "week"
                    ) {
                        composable("today") { }
                        composable("week") {
                            WeekPage(
                                listOfDays = listOfDays,
                                timeEditSheetIsShown = timeEditSheetIsShown,
                                dayEntityClicked = dayEntityClicked,
                            )
                        }
                    }
                }
            }
        }
    }
}
