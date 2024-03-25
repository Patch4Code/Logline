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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.profile.domain.model.getUserProfileData
import com.patch4code.loglinemovieapp.features.profile.presentation.components.ExpandableBio
import com.patch4code.loglinemovieapp.features.profile.presentation.components.MovieFavouriteRow
import com.patch4code.loglinemovieapp.features.profile.presentation.components.ProfileHead
import com.patch4code.loglinemovieapp.features.profile.presentation.components.ProfileNavigation

@Composable
fun ProfileView(navController: NavController, navViewModel: NavigationViewModel) {

    val userData = remember { mutableStateOf(getUserProfileData()) }

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ProfileScreen)
    }

    //Profile Layout
    Column(horizontalAlignment = CenterHorizontally)
    {
        ProfileHead(userData)

        //Edit Button
        IconButton(modifier = Modifier.align(End), onClick = { navController.navigate(Screen.ProfileEditScreen.route) }
        ){
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        //Username
        Text(
            text = userData.value.username,
            modifier = Modifier.align(CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        ProfileNavigation(navController)

        LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
            item{
                ExpandableBio(text = userData.value.bioText)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "FAVOURITE MOVIES")
                MovieFavouriteRow(navController, userData.value.favouriteMovies)
            }
        }
    }
}