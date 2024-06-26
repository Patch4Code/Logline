package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profiles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicProfilesTableView - Composable function representing the public-profiles-table screen view.
 * Shows a list of all public profiles (the current user excluded)
 *
 * @author Patch4Code
 */
@Composable
fun PublicProfilesTableView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    publicProfilesTableViewModel: PublicProfilesTableViewModel = viewModel()
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicProfilesTableScreen)
        publicProfilesTableViewModel.getPublicUserProfiles()
    }
    val isLoading by publicProfilesTableViewModel.isLoading.observeAsState(initial = false)
    val publicUserProfiles = publicProfilesTableViewModel.publicUserProfiles.observeAsState().value

    if(isLoading){
        LoadingIndicator()
    }
    else{
        LazyColumn(modifier = Modifier.padding(16.dp)){
            publicUserProfiles?.forEach{publicProfile->
                item {
                    // User element
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.PublicProfileScreen.route + "/${publicProfile.userId}/${publicProfile.username}")
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        AsyncImage(
                            model = publicProfile.profileImagePath,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(50.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.default_profile_image)
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = publicProfile.username, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
                    }
                }
            }
        }
    }
}