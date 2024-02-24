package com.moritz.movieappuitest.dataclasses.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector
import com.moritz.movieappuitest.Screen

data class BottomNavigationItem(
    val title: String = "",
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Outlined.Home,
    val route: String = ""
) {
    fun getBottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.HomeScreen.route
            ),
            BottomNavigationItem(
                title = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
                route = Screen.SearchScreen.route
            ),
            BottomNavigationItem(
                title = "Profile",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = Screen.ProfileScreen.route
            ),
            BottomNavigationItem(
                title = "Watchlist",
                selectedIcon = Icons.Filled.WatchLater,
                unselectedIcon = Icons.Outlined.WatchLater,
                route = Screen.WatchlistScreen.route
            )
        )
    }
}
