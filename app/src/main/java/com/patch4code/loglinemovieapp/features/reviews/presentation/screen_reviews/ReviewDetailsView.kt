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
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewDetailsInfoAndActions
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewDetailsPoster
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewDetailsView - Composable function representing the review-details screen view.
 * Consists of poster, name, release-year, rating, date of the review, edit and publish button and review text.
 * Lets the user navigate to the MovieView by clicking the movie-poster, navigate to the DiaryEditElementView
 * by clicking the edit button and publish the review by clicking the publish-button (when the user is logged in).
 *
 * @author Patch4Code
 */
@Composable
fun ReviewDetailsView(
    navController: NavController,
    loggedElementId: String?,
    db: LoglineDatabase,
    reviewDetailsViewModel: ReviewDetailsViewModel = viewModel(
        factory = ReviewDetailsViewModelFactory(db.loggedMovieDao)
    )
){
    LaunchedEffect(Unit) {
        loggedElementId?.let { reviewDetailsViewModel.setCurrentReviewedLog(it) }
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.ReviewDetailScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)


    val reviewedLog = reviewDetailsViewModel.currentReviewedLog.observeAsState().value
    val review: String = reviewedLog?.review ?: ""

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                if (reviewedLog != null) {
                    ReviewDetailsPoster(
                        movieTitle = reviewedLog.movie.title,
                        posterPath = reviewedLog.movie.posterUrl,
                        onPosterPressed = {navController.navigate(Screen.MovieScreen.withArgs(
                            reviewedLog.movie.id.toString()))
                        }
                    )
                    ReviewDetailsInfoAndActions(reviewedLog, navController)
                }
            }
            //Review Text
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = review, style = MaterialTheme.typography.bodyMedium)
        }
    }
}