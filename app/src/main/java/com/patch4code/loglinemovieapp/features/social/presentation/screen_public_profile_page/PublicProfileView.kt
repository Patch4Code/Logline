package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.ExpandableBio
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.MovieFavouriteRow
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.ProfileHead
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_profile.PublicProfileNavigation
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

@Composable
fun PublicProfileView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    userId: String?,
    userName: String?,
    publicProfileViewModel: PublicProfileViewModel = viewModel()
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicProfileScreen)
        if (userName != null){
            navViewModel.overrideCurrentScreenTitle(userName)
        }
        publicProfileViewModel.getPublicUserProfile(userId)
    }

    val userProfile = publicProfileViewModel.userProfileData.observeAsState().value
    val isLoading = publicProfileViewModel.isLoading.observeAsState().value

    val context = LocalContext.current
    val dataLoginStore = remember { StoreUserData(context) }
    val yourUserId = dataLoginStore.getUserId.collectAsState(initial = "")
    val isYourProfilePublic = dataLoginStore.getIsProfilePublic.collectAsState(initial = true)


    if (isLoading == true){
        LoadingIndicator()
    }else{
        //Profile Layout
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            ProfileHead(userProfile)

            Spacer(modifier = Modifier.padding(35.dp))

            //Username
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Text(text = userProfile?.username ?: "Anonymous",
                    //modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                if ((isYourProfilePublic.value == false) &&  (yourUserId.value == userId)){
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Profile is Private")
                }
            }


            Spacer(modifier = Modifier.padding(4.dp))

            PublicProfileNavigation(userId ?: "", userName ?: "", navController)

            LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
                item{
                    ExpandableBio(text = userProfile?.bioText ?: "")
                    Spacer(modifier = Modifier.padding(8.dp))
                    MovieFavouriteRow(navController, userProfile?.favouriteMovies ?: emptyList())
                }
            }
        }
    }
}