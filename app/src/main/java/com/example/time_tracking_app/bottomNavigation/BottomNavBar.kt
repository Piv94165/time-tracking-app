package com.example.time_tracking_app.bottomNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavHostController

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
            NavigationBarItem(selected = index == selectedItem.intValue,
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