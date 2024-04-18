package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun PublicProfileListsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    userId: String?,
    userName: String?,
    publicProfileListsViewModel: PublicProfileListsViewModel = viewModel()
){



}