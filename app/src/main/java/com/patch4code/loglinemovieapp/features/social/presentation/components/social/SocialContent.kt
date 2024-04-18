package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

@Composable
fun SocialContent(savedLoginData: StoreUserData, socialViewModel: SocialViewModel, navController: NavController){

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {

            SocialAccountCard(savedLoginData, socialViewModel, navController)

            Spacer(modifier = Modifier.padding(4.dp))

            SocialExploreCard(
                onClick = { navController.navigate(Screen.PublicProfilesTableScreen.route) },
                icon = Icons.Default.Groups2,
                text = "Explore Public Profiles"
            )
            SocialExploreCard(
                onClick = { navController.navigate(Screen.PublicReviewsScreen.route)},
                icon = Icons.Default.Reviews,
                text = "Explore Public Reviews"
            )
            SocialExploreCard(
                onClick = { navController.navigate(Screen.PublicListsTableScreen.route)},
                icon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                text = "Explore Public Lists"
            )
        }
    }
}