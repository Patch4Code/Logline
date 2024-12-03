package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.diary.domain.model.DiaryAndReviewSortOptions
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarSortActions
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.EmptyReviewsText
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewItem
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewsSortBottomSheet
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
    db: LoglineDatabase,
    reviewsViewModel: ReviewsViewModel = viewModel(
        factory = ReviewsViewModelFactory(db.loggedMovieDao)
    )
){

    val selectedSortOption = rememberSaveable { mutableStateOf(DiaryAndReviewSortOptions.ByAddedDesc) }
    val showBottomSheet = remember { mutableStateOf(false)  }

    LaunchedEffect(Unit) {
        reviewsViewModel.getReviewedLogs(selectedSortOption.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.ReviewsScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortActions(onClickAction = { showBottomSheet.value = true })


    val reviewedLogs = reviewsViewModel.reviewedLogs.observeAsState().value

    if(reviewedLogs.isNullOrEmpty()){
        EmptyReviewsText()
    }else{
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(reviewedLogs)
            { loggedItem ->
                ReviewItem(loggedItem, navController)
            }
        }
    }
    ReviewsSortBottomSheet(showBottomSheet, selectedSortOption, reviewsViewModel)
}