package com.patch4code.loglinemovieapp.features.profile.domain.model

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.ui.graphics.vector.ImageVector
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileNavigationElement - Represents an element for navigation on the profile screen.
 *
 * @author Patch4Code
 */
data class ProfileNavigationElement(
    val title: String = "",
    val navIcon: ImageVector  = Icons.Filled.AccountBox,
    val route: String = ""
)
{
    // Returns a list of predefined profile navigation elements.
    fun getProfileNavigationElements(context: Context): List<ProfileNavigationElement>{
        return listOf(
            ProfileNavigationElement(
                title = UiText.StringResource(R.string.movies_text).asString(context),
                navIcon = Icons.Default.Movie,
                route = Screen.MyMoviesScreen.route
            ),
            ProfileNavigationElement(
                title = UiText.StringResource(R.string.diary_text).asString(context),
                navIcon = Icons.Default.DateRange,
                route = Screen.DiaryScreen.route
            ),
            ProfileNavigationElement(
                title = UiText.StringResource(R.string.reviews_text).asString(context),
                navIcon = Icons.Default.Reviews,
                route = Screen.ReviewsScreen.route
            ),
            ProfileNavigationElement(
                title = UiText.StringResource(R.string.lists_text).asString(context),
                navIcon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                route = Screen.ListsTableScreen.route
            )
        )
    }
}