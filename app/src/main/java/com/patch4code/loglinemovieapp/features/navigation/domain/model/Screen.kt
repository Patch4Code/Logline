package com.patch4code.loglinemovieapp.features.navigation.domain.model

import android.util.Log
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText

//to define the Screens for the Navigation
sealed class Screen(val route: String, var title: UiText) {
    object HomeScreen : Screen("home_view", UiText.StringResource(R.string.home_text))
    object ProfileScreen : Screen("profile_view", UiText.StringResource(R.string.profile_text))
    object WatchlistScreen : Screen("watchlist_view", UiText.StringResource(R.string.watchlist_text))
    object SearchScreen : Screen("search_screen", UiText.StringResource(R.string.search_text))
    object AboutScreen : Screen("about_screen", UiText.StringResource(R.string.about_text))
    object MovieScreen : Screen("movie_screen", UiText.StringResource(R.string.empty_text))
    object PersonDetailsScreen : Screen("person_details_screen",UiText.StringResource(R.string.empty_text))
    object MovieLogScreen: Screen("movie_log_screen", UiText.StringResource(R.string.movie_log_text))
    object MyMoviesScreen : Screen("my_movies_screen", UiText.StringResource(R.string.my_movies_text))
    object MoviePublicReviewsScreen: Screen("movie_public_reviews_screen", UiText.StringResource(R.string.empty_text))
    object DiaryScreen : Screen("diary_screen", UiText.StringResource(R.string.diary_text))
    object DiaryEditElementScreen: Screen("diary_edit_element_screen", UiText.StringResource(R.string.diary_edit_element_text))
    object ReviewsScreen : Screen("reviews_screen", UiText.StringResource(R.string.reviews_text))
    object ReviewDetailScreen : Screen("review_detail_screen", UiText.StringResource(R.string.review_detail_text))
    object ListsTableScreen : Screen("lists_screen", UiText.StringResource(R.string.lists_text))
    object ListScreen : Screen("list_screen", UiText.StringResource(R.string.list_text))
    object ProfileEditScreen : Screen("profile_edit_screen", UiText.StringResource(R.string.profile_edit_text))
    object SocialScreen : Screen("social_screen",UiText.StringResource(R.string.social_text))
    object PublicProfilesTableScreen: Screen("public_profiles_table_screen", UiText.StringResource(R.string.public_profiles_text))
    object PublicProfileScreen: Screen("public_profile_screen", UiText.StringResource(R.string.public_profile_text))
    object PublicProfileReviewsScreen: Screen("public_profile_reviews_screen", UiText.StringResource(R.string.public_profile_reviews_text))
    object PublicProfileListsScreen: Screen("public_profile_lists_screen", UiText.StringResource(R.string.public_profile_lists_text))
    object PublicReviewsScreen: Screen("public_reviews_screen", UiText.StringResource(R.string.public_reviews_text))
    object PublicReviewDetailsScreen: Screen("public_review_details_screen", UiText.StringResource(R.string.public_review_details_text))
    object PublicListsTableScreen: Screen("public_lists_table_screen", UiText.StringResource(R.string.public_lists_text))
    object PublicListScreen: Screen("public_list_screen", UiText.StringResource(R.string.public_list_text))
    object SettingsScreen : Screen("settings_screen", UiText.StringResource(R.string.settings_text))

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
