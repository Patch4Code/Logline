package com.patch4code.loglinemovieapp.features.navigation.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerNavigationItem(
    val title: String = "",
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Filled.Home,
    val route: String = "",
){
    fun getDrawerNavigationItems(): List<DrawerNavigationItem> {
        return listOf(
            DrawerNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.HomeScreen.route
            ),
            DrawerNavigationItem(
                title = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
                route = Screen.SearchScreen.route,
            ),
            DrawerNavigationItem(
                title = "Profile",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = Screen.ProfileScreen.route,
            ),
            DrawerNavigationItem(
                title = "Watchlist",
                selectedIcon = Icons.Filled.WatchLater,
                unselectedIcon = Icons.Outlined.WatchLater,
                route = Screen.WatchlistScreen.route,
            ),
            DrawerNavigationItem(
                title = "Friends",
                selectedIcon = Icons.Filled.Group,
                unselectedIcon = Icons.Outlined.Group,
                route = Screen.FriendsScreen.route,
            ),
            DrawerNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = Screen.SettingsScreen.route
            ),
        )
    }
}