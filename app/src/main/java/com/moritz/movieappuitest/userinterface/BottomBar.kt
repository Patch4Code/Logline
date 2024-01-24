package com.moritz.movieappuitest.userinterface

import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.BottomNavigationItem

@Composable
fun BottomBar(navController: NavController, screenIndex: Int){

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(screenIndex)
    }

    NavigationBar {
        BottomNavigationItem().getBottomNavigationItems().forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                label = {
                    Text(text = item.title, color = Color.White)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                onClick = {
                    selectedItemIndex = index
                    //Navigation
                    navController.navigate(item.route)

                }
            )
        }
    }
}