package com.moritz.movieappuitest.userinterface

import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.BottomNavigationItem

@Composable
fun BottomBar(navController: NavController, currentScreenTitle: String){

    NavigationBar {
        BottomNavigationItem().getBottomNavigationItems().forEachIndexed { index, item ->
            NavigationBarItem(

                selected = item.title == currentScreenTitle,
                label = {
                    Text(text = item.title, color = Color.White)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (item.title == currentScreenTitle) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                onClick = {
                    //Navigation
                    navController.navigate(item.route)
                }
            )
        }
    }
}