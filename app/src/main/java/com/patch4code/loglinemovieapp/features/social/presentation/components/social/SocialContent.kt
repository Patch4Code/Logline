package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

@Composable
fun SocialContent(savedLoginData: StoreUserData, socialViewModel: SocialViewModel, navController: NavController){

    val savedLoginId = savedLoginData.getUserId.collectAsState(initial = "")

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {


            if (savedLoginId.value?.isNotEmpty() == true){
                SocialAccountCard(savedLoginData, socialViewModel, navController)
            } else{
                SocialLoginCard()
            }

            Spacer(modifier = Modifier.padding(4.dp))

            SocialExploreCard(
                onClick = { navController.navigate(Screen.PublicProfilesTableScreen.route) },
                icon = Icons.Default.Groups2,
                text = stringResource(id = R.string.explore_public_profiles_title)
            )
            SocialExploreCard(
                onClick = { navController.navigate(Screen.PublicReviewsScreen.route)},
                icon = Icons.Default.Reviews,
                text = stringResource(id = R.string.explore_public_reviews_title)
            )
            SocialExploreCard(
                onClick = { navController.navigate(Screen.PublicListsTableScreen.route)},
                icon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                text = stringResource(id = R.string.explore_public_lists_title)
            )
        }
    }
}