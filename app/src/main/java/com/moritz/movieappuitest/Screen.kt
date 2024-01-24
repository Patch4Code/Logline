package com.moritz.movieappuitest

//to define the Screens for the Navigation
sealed class Screen(val route: String) {
    object MainScreen : Screen("main_view")
    object ProfileScreen : Screen("profile_view")
    object SocialScreen : Screen("social_view")

}
