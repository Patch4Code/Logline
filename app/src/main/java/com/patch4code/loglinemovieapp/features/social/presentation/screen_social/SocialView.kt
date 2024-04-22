package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.SocialContent
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun SocialView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    socialViewModel: SocialViewModel = viewModel(
        factory = SocialViewModelFactory(db.userProfileDao)

    )
){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SocialScreen)
        socialViewModel.initializeDataStore(context)
    }

    val dataLoginStore = remember { StoreUserData(context) }

    SocialContent(dataLoginStore, socialViewModel, navController)
}