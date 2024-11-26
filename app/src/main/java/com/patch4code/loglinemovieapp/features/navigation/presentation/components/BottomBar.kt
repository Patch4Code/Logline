package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.patch4code.loglinemovieapp.features.navigation.domain.model.BottomNavigationItem

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * BottomBar - Composable function for displaying the bottom navigation bar.
 * Uses predefined list of bottom navigation items and displays a NavigationBarItem for each element.
 * Provides navigation to predefined destination on click.
 *
 * @author Patch4Code
 */
@Composable
fun BottomBar(navController: NavController){

    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavigationItem().getBottomNavigationItems(context).forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                label = { Text(text = item.title) },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (item.route == currentRoute) {
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