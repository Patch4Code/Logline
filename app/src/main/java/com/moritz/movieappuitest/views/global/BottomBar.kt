package com.moritz.movieappuitest.views.global

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.navigation.BottomNavigationItem
import com.moritz.movieappuitest.viewmodels.NavigationViewModel

@Composable
fun BottomBar(navController: NavController, navViewModel: NavigationViewModel){

    val currentScreen by navViewModel.currentScreen.observeAsState(Screen.HomeScreen)

    NavigationBar {
        BottomNavigationItem().getBottomNavigationItems().forEach { item ->
            NavigationBarItem(

                selected = item.title == currentScreen.title,
                label = {
                    Text(text = item.title, color = Color.White)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (item.title == currentScreen.title) {
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