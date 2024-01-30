package com.moritz.movieappuitest.dataclasses

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileNavigationElement(
    val title: String = "",
    val navIcon: ImageVector  = Icons.Filled.AccountBox,
    val route: String = ""
)
{
    fun getProfileNavigationElements(): List<ProfileNavigationElement>{
        return listOf(
            ProfileNavigationElement(
                title = "Movies",
                navIcon = Icons.Filled.AccountBox,
                //route = Screen..route
            ),
            ProfileNavigationElement(
                title = "Diary",
                navIcon = Icons.Filled.DateRange,
                //route = Screen..route
            ),
            ProfileNavigationElement(
                title = "Watchlist",
                navIcon = Icons.Filled.Menu,
                //route = Screen..route
            ),
            ProfileNavigationElement(
                title = "Lists",
                navIcon = Icons.Filled.List,
                //route = Screen..route
            )
        )
    }
}
