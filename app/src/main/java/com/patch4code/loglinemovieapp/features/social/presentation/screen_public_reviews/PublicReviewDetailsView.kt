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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewDetailsPoster
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews.PublicReviewDetailsInfoAndActions
import java.net.URLDecoder

@Composable
fun PublicReviewDetailsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    loglineReviewJson: String?,
    publicReviewDetailsViewModel: PublicReviewDetailsViewModel = viewModel()
){

    val decodedLoglineReviewJson = URLDecoder.decode(loglineReviewJson, "UTF-8")
    val review: LoglineReview = JSONHelper.fromJson(decodedLoglineReviewJson)

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicReviewDetailsScreen)
        navViewModel.overrideCurrentScreenTitle("Review by ${review.authorName}")
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


    //Text(text = "${review.movie}, ${review.objectId}, ${review.authorName}, isYourReview = $isYourReview")

}