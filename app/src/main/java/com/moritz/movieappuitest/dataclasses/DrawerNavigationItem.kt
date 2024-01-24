package com.moritz.movieappuitest.dataclasses

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerNavigationItem(
    val title: String = "",
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Filled.Home,
){
    fun getDrawerNavigationItems(): List<DrawerNavigationItem> {
        return listOf(
            DrawerNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
            ),
            DrawerNavigationItem(
                title = "Search",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
            ),
            DrawerNavigationItem(
                title = "Profile",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
            ),
            DrawerNavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
            ),
        )
    }
}