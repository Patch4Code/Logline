package com.moritz.movieappuitest.features.settings.presentation.screen_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun SettingsView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SettingsScreen)
    }

    Column()
    {
        Text(text = "Settings View")
    }
}