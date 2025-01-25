package com.patch4code.logline.features.profile.presentation.screen_profile

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
import com.patch4code.logline.features.navigation.domain.model.Screen
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarBackNavigationIcon
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.logline.features.profile.presentation.components.profile_edit.ProfileEditBannerSection
import com.patch4code.logline.features.profile.presentation.components.profile_edit.ProfileEditBioSection
import com.patch4code.logline.features.profile.presentation.components.profile_edit.ProfileEditFavMoviesSection
import com.patch4code.logline.features.profile.presentation.components.profile_edit.ProfileEditImageSection
import com.patch4code.logline.features.profile.presentation.components.profile_edit.ProfileEditNameSection
import com.patch4code.logline.features.profile.presentation.components.profile_edit.dialogs.EditBioDialog
import com.patch4code.logline.features.profile.presentation.components.profile_edit.dialogs.EditProfileNameDialog
import com.patch4code.logline.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileEditView - Composable function representing the profile-edit screen view.
 * Provides functionality for editing the user profile.
 *
 * @author Patch4Code
 */
@Composable
fun ProfileEditView(
    navController: NavController,
    db: LoglineDatabase,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(db.userProfileDao)
    )
){

    LaunchedEffect(Unit) {
        profileViewModel.getUserProfileData()
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.ProfileEditScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)


    val userProfileData = profileViewModel.userProfileData.observeAsState().value

    val openEditProfileNameDialog = remember { mutableStateOf(false)  }
    val openEditBioDialog = remember { mutableStateOf(false)  }

    LazyColumn(modifier = Modifier.padding(16.dp))
    {
        item {
            ProfileEditNameSection(openEditProfileNameDialog, userProfileData?.userProfile?.username)
            ProfileEditBioSection(openEditBioDialog, userProfileData?.userProfile?.bioText)

            Row {
                ProfileEditImageSection(userProfileData?.userProfile, profileViewModel, navController)
                ProfileEditBannerSection(userProfileData?.userProfile, profileViewModel, navController)
            }
            ProfileEditFavMoviesSection(userProfileData?.favouriteMovies ?: emptyList(), profileViewModel)
        }
    }
    EditProfileNameDialog(openEditProfileNameDialog, userProfileData?.userProfile?.username, profileViewModel)
    EditBioDialog(openEditBioDialog, userProfileData?.userProfile?.bioText, profileViewModel)
}