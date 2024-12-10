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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.patch4code.loglinemovieapp.features.navigation.domain.model.DrawerNavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DrawerContent - Composable function to display the content of the navigation drawer.
 * Uses predefined list of drawer navigation items and displays a NavigationDrawerItem for each element.
 * Provides navigation to predefined destination on click.
 *
 * @author Patch4Code
 */
@Composable
fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
){
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalDrawerSheet (
        modifier = Modifier.width(300.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        DrawerNavigationItem().getDrawerNavigationItems(context).forEach { drawerNavigationItem ->
            NavigationDrawerItem(
                label = { Text(text = drawerNavigationItem.title) },
                selected = drawerNavigationItem.route == currentRoute,
                onClick = {
                    scope.launch{
                        drawerState.close()
                    }
                    navController.navigate(drawerNavigationItem.route){
                        popUpTo(drawerNavigationItem.route) { inclusive = true }
                    }

                },
                icon = {
                    Icon(
                        imageVector = if(drawerNavigationItem.route == currentRoute) {
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