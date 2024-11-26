package com.patch4code.loglinemovieapp.features.navigation.domain.model

import android.util.Log
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Screen - Represents the screens for navigation within the app.
 * This sealed class defines all screens along with their routes and titles.
 *
 * @author Patch4Code
 */
sealed class Screen(val route: String, var title: UiText) {
    data object HomeScreen : Screen("home_view", UiText.StringResource(R.string.home_text))
    data object ProfileScreen : Screen("profile_view", UiText.StringResource(R.string.profile_text))
    data object WatchlistScreen : Screen("watchlist_view", UiText.StringResource(R.string.watchlist_text))
    data object SearchScreen : Screen("search_screen", UiText.StringResource(R.string.search_text))
    data object AboutScreen : Screen("about_screen", UiText.StringResource(R.string.about_text))
    data object MovieScreen : Screen("movie_screen", UiText.StringResource(R.string.empty_text))
    data object PersonDetailsScreen : Screen("person_details_screen",UiText.StringResource(R.string.empty_text))
    data object MovieLogScreen: Screen("movie_log_screen", UiText.StringResource(R.string.movie_log_text))
    data object MyMoviesScreen : Screen("my_movies_screen", UiText.StringResource(R.string.my_movies_text))
    data object MoviePublicReviewsScreen: Screen("movie_public_reviews_screen", UiText.StringResource(R.string.empty_text))
    data object DiaryScreen : Screen("diary_screen", UiText.StringResource(R.string.diary_text))
    data object DiaryEditElementScreen: Screen("diary_edit_element_screen", UiText.StringResource(R.string.diary_edit_element_text))
    data object ReviewsScreen : Screen("reviews_screen", UiText.StringResource(R.string.reviews_text))
    data object ReviewDetailScreen : Screen("review_detail_screen", UiText.StringResource(R.string.review_detail_text))
    data object ListsTableScreen : Screen("lists_screen", UiText.StringResource(R.string.lists_text))
    data object ListScreen : Screen("list_screen", UiText.StringResource(R.string.list_text))
    data object ProfileEditScreen : Screen("profile_edit_screen", UiText.StringResource(R.string.profile_edit_text))
    //object SocialScreen : Screen("social_screen",UiText.StringResource(R.string.social_text))
    //object PublicProfilesTableScreen: Screen("public_profiles_table_screen", UiText.StringResource(R.string.public_profiles_text))
    data object PublicProfileScreen: Screen("public_profile_screen", UiText.StringResource(R.string.public_profile_text))
    //object PublicProfileReviewsScreen: Screen("public_profile_reviews_screen", UiText.StringResource(R.string.public_profile_reviews_text))
    //object PublicProfileListsScreen: Screen("public_profile_lists_screen", UiText.StringResource(R.string.public_profile_lists_text))
    //object PublicReviewsScreen: Screen("public_reviews_screen", UiText.StringResource(R.string.public_reviews_text))
    //object PublicReviewDetailsScreen: Screen("public_review_details_screen", UiText.StringResource(R.string.public_review_details_text))
    //object PublicListsTableScreen: Screen("public_lists_table_screen", UiText.StringResource(R.string.public_lists_text))
    //object PublicListScreen: Screen("public_list_screen", UiText.StringResource(R.string.public_list_text))
    data object SettingsScreen : Screen("settings_screen", UiText.StringResource(R.string.settings_text))

    //helper to attach data for navigation
    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
                Log.e("Screen", "Arg: $arg")
            }
        }
    }
}
