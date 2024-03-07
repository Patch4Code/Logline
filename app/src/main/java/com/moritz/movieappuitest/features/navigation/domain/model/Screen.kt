package com.moritz.movieappuitest.features.navigation.domain.model

import android.util.Log

//to define the Screens for the Navigation
sealed class Screen(val route: String, val title: String) {
    object HomeScreen : Screen("home_view", "Home")
    object ProfileScreen : Screen("profile_view", "Profile")
    object WatchlistScreen : Screen("watchlist_view", "Watchlist")
    object SearchScreen : Screen("search_screen", "Search")
    object SettingsScreen : Screen("settings_screen", "Settings")
    object MovieScreen : Screen("movie_screen", "")
    object MyMoviesScreen : Screen("my_movies_screen", "Movies")
    object DiaryScreen : Screen("diary_screen", "Diary")
    object DiaryEditElementScreen: Screen("diary_edit_element_screen", "Edit Diary-Entry")
    object ReviewsScreen : Screen("reviews_screen", "Reviews")
    object ReviewDetailScreen : Screen("review_detail_screen", "Review")
    object ListsTableScreen : Screen("lists_screen", "Lists")
    object ListScreen : Screen("list_screen", "List")
    object ProfileEditScreen : Screen("profile_edit_screen", "Edit Profile")
    object FriendsScreen : Screen("friends_screen","Friends")

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
