package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.datastore.StoreUserData
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.SocialContent

@Composable
fun SocialView(navController: NavController, navViewModel: NavigationViewModel, socialViewModel: SocialViewModel = viewModel()){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SocialScreen)
        socialViewModel.initializeDataStore(context)
    }

    val dataLoginStore = remember { StoreUserData(context) }
    val savedLoginData = dataLoginStore.getUserId.collectAsState(initial = "")

    if (savedLoginData.value?.isNotEmpty() == true){
        SocialContent(dataLoginStore, socialViewModel)
    } else{
        LoginView(socialViewModel)
    }
}