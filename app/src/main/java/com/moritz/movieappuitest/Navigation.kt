package com.moritz.movieappuitest

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moritz.movieappuitest.userinterface.BottomBar
import com.moritz.movieappuitest.userinterface.MainView
import com.moritz.movieappuitest.userinterface.ProfileView
import com.moritz.movieappuitest.userinterface.SearchView
import com.moritz.movieappuitest.userinterface.SocialView
import com.moritz.movieappuitest.userinterface.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var currentScreenTitle by remember { mutableStateOf("") }

    Scaffold (
        bottomBar = {
            if(currentScreenTitle == "Home" || currentScreenTitle == "Profile" || currentScreenTitle == "Social"){
                BottomBar(navController, 0)
            }

        },
        topBar = {
            if(currentScreenTitle == "Home" || currentScreenTitle == "Profile" || currentScreenTitle == "Social"){
                TopBar(navController, currentScreenTitle, scrollBehavior)
            }
        }
    )
    {padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MainScreen.route,
            modifier = Modifier.padding(padding))
        {

            composable(route = Screen.MainScreen.route){
                currentScreenTitle = Screen.MainScreen.title
                MainView(navController = navController)
            }

            composable(route = Screen.ProfileScreen.route){
                currentScreenTitle = Screen.ProfileScreen.title
                ProfileView(navController = navController)
            }

            composable(route = Screen.SocialScreen.route){
                currentScreenTitle = Screen.SocialScreen.title
                SocialView(navController = navController)
            }

            composable(route = Screen.SearchScreen.route){
                currentScreenTitle = Screen.SearchScreen.title
                SearchView(navController = navController)
            }
        }
    }


}