package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.DrawerNavigationItem
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    navController: NavController,
    navViewModel: NavigationViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navigationViewModel: NavigationViewModel
){
    val context = LocalContext.current
    val currentScreen by navViewModel.currentScreen.observeAsState(Screen.HomeScreen)

    ModalDrawerSheet (
        modifier = Modifier.width(300.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        DrawerNavigationItem().getDrawerNavigationItems(context).forEach { drawerNavigationItem ->
            NavigationDrawerItem(
                label = { Text(text = drawerNavigationItem.title) },
                selected = drawerNavigationItem.title == currentScreen.title.asString(),
                onClick = {
                    scope.launch{
                        drawerState.close()
                    }
                    navController.navigate(drawerNavigationItem.route)

                },
                icon = {
                    Icon(
                        imageVector = if(drawerNavigationItem.title == currentScreen.title.asString()) {
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