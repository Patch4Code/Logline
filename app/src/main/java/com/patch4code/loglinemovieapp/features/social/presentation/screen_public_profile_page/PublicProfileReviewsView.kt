package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_profile.PublicProfileReviewItem

@Composable
fun PublicProfileReviewsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    userId: String?,
    userName: String?,
    publicProfileReviewsViewModel: PublicProfileReviewsViewModel = viewModel()
){
    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicProfileReviewsScreen)
        navViewModel.overrideCurrentScreenTitle("$userName's Reviews")
        publicProfileReviewsViewModel.getPublicProfileReviews(userId)
    }

    val publicProfileReviews = publicProfileReviewsViewModel.publicProfileReviews.observeAsState().value
    val isLoading by publicProfileReviewsViewModel.isLoading.observeAsState(initial = false)

    if(isLoading){
        LoadingIndicator()
    }else{
        if (publicProfileReviews.isNullOrEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "no reviews")
            }
            return
        }

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(publicProfileReviews)
            { loglineReview ->
                PublicProfileReviewItem(loglineReview, navController)
            }
        }
    }
}