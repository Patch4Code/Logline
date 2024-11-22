package com.patch4code.loglinemovieapp.features.navigation.domain.model

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.automirrored.outlined.FeaturedPlayList
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DrawerNavigationItem - Represents an item in the drawer navigation menu.
 *
 * @author Patch4Code
 */
data class DrawerNavigationItem(
    val title: String = "",
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Filled.Home,
    val route: String = "",
){
    // Returns a list of predefined drawer navigation items.
    fun getDrawerNavigationItems(context: Context): List<DrawerNavigationItem> {
        return listOf(
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.home_text).asString(context),
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.HomeScreen.route
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.search_text).asString(context),
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
                route = Screen.SearchScreen.route,
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.profile_text).asString(context),
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = Screen.ProfileScreen.route,
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.watchlist_text).asString(context),
                selectedIcon = Icons.Filled.WatchLater,
                unselectedIcon = Icons.Outlined.WatchLater,
                route = Screen.WatchlistScreen.route,
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.movies_text).asString(context),
                selectedIcon = Icons.Filled.Movie,
                unselectedIcon = Icons.Outlined.Movie,
                route = Screen.MyMoviesScreen.route,
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.diary_text).asString(context),
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
                route = Screen.DiaryScreen.route,
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.reviews_text).asString(context),
                selectedIcon = Icons.Filled.Reviews,
                unselectedIcon = Icons.Outlined.Reviews,
                route = Screen.ReviewsScreen.route,
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.lists_text).asString(context),
                selectedIcon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                unselectedIcon = Icons.AutoMirrored.Outlined.FeaturedPlayList,
                route = Screen.ListsTableScreen.route,
            ),
            // TODO: This element is currently unused due to temporary deactivation of social media features.
            /*
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.social_text).asString(context),
                selectedIcon = Icons.Filled.Public,
                unselectedIcon = Icons.Outlined.Public,
                route = Screen.SocialScreen.route,
            )
             */
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.settings_text).asString(context),
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = Screen.SettingsScreen.route
            ),
            DrawerNavigationItem(
                title = UiText.StringResource(R.string.about_text).asString(context),
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
                route = Screen.AboutScreen.route
            )
        )
    }
}