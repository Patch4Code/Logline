package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.ui.graphics.vector.ImageVector
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

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
                navIcon = Icons.Default.Movie,
                route = Screen.MyMoviesScreen.route
            ),
            ProfileNavigationElement(
                title = "Diary",
                navIcon = Icons.Default.DateRange,
                route = Screen.DiaryScreen.route
            ),
            ProfileNavigationElement(
                title = "Reviews",
                navIcon = Icons.Default.Reviews,
                route = Screen.ReviewsScreen.route
            ),
            ProfileNavigationElement(
                title = "Lists",
                navIcon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                route = Screen.ListsTableScreen.route
            )
        )
    }
}