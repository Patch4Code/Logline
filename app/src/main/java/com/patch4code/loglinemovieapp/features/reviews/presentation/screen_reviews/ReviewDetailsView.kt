package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewDetailsInfo
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewDetailsPoster
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun ReviewDetailsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    loggedElementId: String?,
    db: LoglineDatabase,
    reviewDetailsViewModel: ReviewDetailsViewModel = viewModel(
        factory = ReviewDetailsViewModelFactory(db.loggedMovieDao)
    )
){
    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewDetailScreen)
        loggedElementId?.let { reviewDetailsViewModel.setCurrentReviewedLog(it) }
    }

    val reviewedLog = reviewDetailsViewModel.currentReviewedLog.observeAsState().value
    val review: String = reviewedLog?.review ?: ""

    val comingFromDiaryView = navController.previousBackStackEntry?.destination?.route == Screen.DiaryScreen.route

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                if (reviewedLog != null) {
                    ReviewDetailsPoster(
                        loggedElementData = reviewedLog,
                        onPosterPressed = {navController.navigate(Screen.MovieScreen.withArgs(
                            reviewedLog.movie.id.toString()))
                        }
                    )
                }

                if (reviewedLog != null) {
                    ReviewDetailsInfo(
                        reviewedLog = reviewedLog,
                        onEditPressed = {reviewedLogId->
                            navController.navigate("${Screen.DiaryEditElementScreen.route}/${reviewedLogId}/$comingFromDiaryView")
                        }
                    )
                }
            }
            //Review Text
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = review, style = MaterialTheme.typography.bodyMedium)
        }
    }
}