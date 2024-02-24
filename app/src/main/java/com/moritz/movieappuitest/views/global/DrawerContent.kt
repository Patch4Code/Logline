package com.moritz.movieappuitest.views.global

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.navigation.DrawerNavigationItem
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(navController: NavController, navViewModel: NavigationViewModel,drawerState: DrawerState, scope: CoroutineScope, navigationViewModel: NavigationViewModel){

    val currentScreen by navViewModel.currentScreen.observeAsState(Screen.HomeScreen)

    ModalDrawerSheet (
        modifier = Modifier.width(300.dp)
    )
    {
        Spacer(modifier = Modifier.height(16.dp))
        DrawerNavigationItem().getDrawerNavigationItems().forEach { drawerNavigationItem ->
            NavigationDrawerItem(
                label = { Text(text = drawerNavigationItem.title) },
                selected = drawerNavigationItem.title == currentScreen.title,
                onClick = {
                    scope.launch{
                        drawerState.close()
                    }
                    navController.navigate(drawerNavigationItem.route)

                },
                icon = {
                    Icon(
                        imageVector = if(drawerNavigationItem.title == currentScreen.title) {
                            drawerNavigationItem.selectedIcon
                        } else {drawerNavigationItem.unselectedIcon},
                        contentDescription = drawerNavigationItem.title
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}