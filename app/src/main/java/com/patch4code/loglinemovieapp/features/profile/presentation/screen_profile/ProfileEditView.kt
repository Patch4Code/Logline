package com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun ProfileEditView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ProfileEditScreen)
    }

    Column()
    {
        Text(text = "ProfileEdit View")
    }
}