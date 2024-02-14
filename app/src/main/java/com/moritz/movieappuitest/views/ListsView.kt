package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.viewmodels.NavigationViewModel

@Composable
fun ListsView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ListsScreen)
    }

    //Profile Layout
    Column()
    {
        Text(text = "Lists View")
    }
}