package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews.PublicReviewItem

@Composable
fun PublicReviewsView(navController: NavController, navViewModel: NavigationViewModel, publicReviewsViewModel: PublicReviewsViewModel = viewModel()){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicReviewsScreen)
        publicReviewsViewModel.getPublicReviews()
    }

    val isLoading by publicReviewsViewModel.isLoading.observeAsState(initial = false)
    val publicReviews = publicReviewsViewModel.publicReviews.observeAsState().value

    if(isLoading){
        LoadingIndicator()
    }else{
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(publicReviews ?: emptyList())
            { loglineReview ->
                PublicReviewItem(loglineReview, navController)
            }
        }
    }
}