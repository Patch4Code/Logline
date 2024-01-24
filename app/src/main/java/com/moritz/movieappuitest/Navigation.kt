package com.moritz.movieappuitest

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moritz.movieappuitest.userinterface.MainView
import com.moritz.movieappuitest.userinterface.ProfileView
import com.moritz.movieappuitest.userinterface.SocialView

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainView(navController = navController)
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileView(navController = navController)
        }

        composable(route = Screen.SocialScreen.route){
            SocialView(navController = navController)
        }
    }
}