package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewDetailsPoster
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews.PublicReviewDetailsInfoAndActions
import java.net.URLDecoder

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicReviewDetailsView - Composable function representing the public-review-details screen view.
 * Shows the full public  review of a user with all related information.
 *
 * @author Patch4Code
 */
@Composable
fun PublicReviewDetailsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    loglineReviewJson: String?,
    publicReviewDetailsViewModel: PublicReviewDetailsViewModel = viewModel()
){

    val context = LocalContext.current

    val decodedLoglineReviewJson = URLDecoder.decode(loglineReviewJson, "UTF-8")
    val review: LoglineReview = JSONHelper.fromJson(decodedLoglineReviewJson)

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicReviewDetailsScreen)
        navViewModel.overrideCurrentScreenTitle(UiText.DynamicString("${UiText.StringResource(R.string.review_by_text).asString(context)} ${review.authorName}"))
        publicReviewDetailsViewModel.isYourReview(review.userId)
    }

    val isYourReview = publicReviewDetailsViewModel.isYourReview.observeAsState().value

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                ReviewDetailsPoster(
                    movieTitle = review.movie.title,
                    posterPath = review.movie.posterUrl,
                    onPosterPressed = {navController.navigate(Screen.MovieScreen.withArgs(
                        review.movie.id.toString()))
                    }
                )
                PublicReviewDetailsInfoAndActions(review, navController, isYourReview, publicReviewDetailsViewModel)
            }
            //Review Text
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = review.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}