package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewItem
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewsView - Composable function representing the review screen view.
 * Displays a list of the users reviews (logs that contain a review).
 * Navigates the user to the ReviewDetailsView on click on ReviewItem.
 *
 * @author Patch4Code
 */
@Composable
fun ReviewsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    reviewsViewModel: ReviewsViewModel = viewModel(
        factory = ReviewsViewModelFactory(db.loggedMovieDao)
    )
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewsScreen)
        reviewsViewModel.getReviewedLogs()
    }

    val reviewedLogs = reviewsViewModel.reviewedLogs.observeAsState().value

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(reviewedLogs ?: emptyList())
        { loggedItem ->
            ReviewItem(loggedItem, navController)
        }
    }
}