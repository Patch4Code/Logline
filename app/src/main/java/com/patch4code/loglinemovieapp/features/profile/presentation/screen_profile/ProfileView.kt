package com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.ExpandableBio
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.MovieFavouriteRow
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.ProfileHead
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile.ProfileNavigation
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun ProfileView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(db.userProfileDao)
    )
) {

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ProfileScreen)
        profileViewModel.getUserProfileData()
    }

    val userProfile = profileViewModel.userProfileData.observeAsState().value

    //Profile Layout
    Column(horizontalAlignment = CenterHorizontally)
    {
        ProfileHead(userProfile)

        //Edit Button
        IconButton(modifier = Modifier.align(End), onClick = { navController.navigate(Screen.ProfileEditScreen.route) }
        ){
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        //Username
        Text(
            text = userProfile?.username ?: "Anonymous",
            modifier = Modifier.align(CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        ProfileNavigation(navController)

        LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
            item{
                ExpandableBio(text = userProfile?.bioText ?: "")
                Spacer(modifier = Modifier.padding(8.dp))
                MovieFavouriteRow(navController, userProfile?.favouriteMovies ?: emptyList())
            }
        }
    }
}