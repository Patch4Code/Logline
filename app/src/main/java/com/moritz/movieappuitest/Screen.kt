package com.moritz.movieappuitest

//to define the Screens for the Navigation
sealed class Screen(val route: String, val title: String) {
    object MainScreen : Screen("main_view", "Home")
    object ProfileScreen : Screen("profile_view", "Profile")
    object SocialScreen : Screen("social_view", "Social")
    object SearchScreen : Screen("search_screen", "Search")

}
