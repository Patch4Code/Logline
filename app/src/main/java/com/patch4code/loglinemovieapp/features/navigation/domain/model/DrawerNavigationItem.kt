package com.patch4code.loglinemovieapp.features.navigation.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.automirrored.outlined.FeaturedPlayList
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Reviews
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
                title = "Movies",
                selectedIcon = Icons.Filled.Movie,
                unselectedIcon = Icons.Outlined.Movie,
                route = Screen.MyMoviesScreen.route,
            ),
            DrawerNavigationItem(
                title = "Diary",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
                route = Screen.DiaryScreen.route,
            ),
            DrawerNavigationItem(
                title = "Reviews",
                selectedIcon = Icons.Filled.Reviews,
                unselectedIcon = Icons.Outlined.Reviews,
                route = Screen.ReviewsScreen.route,
            ),
            DrawerNavigationItem(
                title = "Lists",
                selectedIcon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                unselectedIcon = Icons.AutoMirrored.Outlined.FeaturedPlayList,
                route = Screen.ListsTableScreen.route,
            ),
            DrawerNavigationItem(
                title = "Social",
                selectedIcon = Icons.Filled.Public,
                unselectedIcon = Icons.Outlined.Public,
                route = Screen.SocialScreen.route,
            ),
            DrawerNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = Screen.SettingsScreen.route
            ),
            DrawerNavigationItem(
                title = "About",
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
                route = Screen.AboutScreen.route
            )
        )
    }
}