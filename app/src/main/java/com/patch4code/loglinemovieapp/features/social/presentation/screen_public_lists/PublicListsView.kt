package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_lists

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun PublicListsView(navController: NavController, navViewModel: NavigationViewModel, publicListsViewViewModel: PublicListsViewModel = viewModel()){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicListsScreen)
    }

    Text(text = "PublicListsView")

}