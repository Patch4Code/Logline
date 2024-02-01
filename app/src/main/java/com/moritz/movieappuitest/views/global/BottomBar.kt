package com.moritz.movieappuitest.views.global

import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.BottomNavigationItem
import com.moritz.movieappuitest.viewmodels.NavigationViewModel

@Composable
fun BottomBar(navController: NavController, navViewModel: NavigationViewModel){

    NavigationBar {
        BottomNavigationItem().getBottomNavigationItems().forEach {item ->
            NavigationBarItem(

                selected = item.title == navViewModel.currentScreenTitle.value,
                label = {
                    Text(text = item.title, color = Color.White)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (item.title == navViewModel.currentScreenTitle.value) {
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