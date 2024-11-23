package com.patch4code.loglinemovieapp.features.navigation.domain.model

import android.content.Context
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
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * BottomNavigationItem - Represents an item in the bottom navigation bar.
 *
 * @author Patch4Code
 */
data class BottomNavigationItem(
    val title: String = "",
    val selectedIcon: ImageVector = Icons.Filled.Home,
    val unselectedIcon: ImageVector = Icons.Outlined.Home,
    val route: String = ""
) {
    // Returns a list of predefined bottom navigation items.
    fun getBottomNavigationItems(context: Context): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                title = UiText.StringResource(R.string.home_text).asString(context),
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.HomeScreen.route
            ),
            BottomNavigationItem(
                title = UiText.StringResource(R.string.search_text).asString(context),
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
                route = Screen.SearchScreen.route
            ),
            BottomNavigationItem(
                title = UiText.StringResource(R.string.watchlist_text).asString(context),
                selectedIcon = Icons.Filled.WatchLater,
                unselectedIcon = Icons.Outlined.WatchLater,
                route = Screen.WatchlistScreen.route
            ),
            BottomNavigationItem(
                title = UiText.StringResource(R.string.profile_text).asString(context),
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = Screen.ProfileScreen.route
            )
        )
    }
}
