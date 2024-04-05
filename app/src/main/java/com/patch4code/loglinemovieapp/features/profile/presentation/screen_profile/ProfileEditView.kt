package com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.EditBioDialog
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.EditProfileNameDialog
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.ProfileEditBannerSection
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.ProfileEditBioSection
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.ProfileEditFavMoviesSection
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.ProfileEditImageSection
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.ProfileEditNameSection
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun ProfileEditView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(db.userProfileDao)
    )
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ProfileEditScreen)
        profileViewModel.getUserProfileData()
    }

    val userProfile = profileViewModel.userProfileData.observeAsState().value

    val openEditProfileNameDialog = remember { mutableStateOf(false)  }
    val openEditBioDialog = remember { mutableStateOf(false)  }

    LazyColumn(modifier = Modifier.padding(16.dp))
    {
        item {
            ProfileEditNameSection(openEditProfileNameDialog, userProfile?.username)
            ProfileEditBioSection(openEditBioDialog, userProfile?.bioText)

            Row {
                ProfileEditImageSection(userProfile)
                ProfileEditBannerSection(userProfile)
            }
            ProfileEditFavMoviesSection(userProfile)
        }
    }
    EditProfileNameDialog(openEditProfileNameDialog, userProfile?.username, profileViewModel)
    EditBioDialog(openEditBioDialog, userProfile?.bioText, profileViewModel)
}