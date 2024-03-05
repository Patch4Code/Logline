package com.moritz.movieappuitest.features.reviews.presentation.screen_reviews

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun ReviewsView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewsScreen)
    }

    //Profile Layout
    Column()
    {
        Text(text = "Reviews View")
    }
}