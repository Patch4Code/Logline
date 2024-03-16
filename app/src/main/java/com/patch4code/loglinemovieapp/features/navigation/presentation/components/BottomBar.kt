package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.BottomNavigationItem
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun BottomBar(navController: NavController, navViewModel: NavigationViewModel){

    val currentScreen by navViewModel.currentScreen.observeAsState(Screen.HomeScreen)

    NavigationBar {
        BottomNavigationItem().getBottomNavigationItems().forEach { item ->
            NavigationBarItem(

                selected = item.title == currentScreen.title,
                label = {
                    Text(text = item.title)
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
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}