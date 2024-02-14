package com.moritz.movieappuitest

//to define the Screens for the Navigation
sealed class Screen(val route: String, val title: String) {
    object HomeScreen : Screen("home_view", "Home")
    object ProfileScreen : Screen("profile_view", "Profile")
    object WatchlistScreen : Screen("watchlist_view", "Watchlist")
    object SearchScreen : Screen("search_screen", "Search")
    object SettingsScreen : Screen ("settings_screen", "Settings")
    object MovieScreen : Screen ("movie_screen", "")
    object MyMoviesScreen : Screen ("my_movies_screen", "Movies")
    object DiaryScreen : Screen ("diary_screen", "Diary")
    object ReviewsScreen : Screen ("reviews_screen", "Reviews")
    object ListsScreen : Screen ("lists_screen", "Lists")
    object ProfileEditScreen : Screen ("profile_edit_screen", "Edit Profile")
    object FriendsScreen : Screen ("friends_screen","Friends")

    //helper to attach data for navigation
    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }
}
