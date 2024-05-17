package com.example.time_tracking_app.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import java.lang.reflect.Modifier

@Composable
fun BottomNavBar(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = "today") {
        composable("today") { }
        composable("week") {
            /*WeekPage(
                listOfDays = listOfDays,
                timeEditSheetIsShown = timeEditSheetIsShown,
                dayClicked = dayClicked,
            )
             */
        }
    }
}