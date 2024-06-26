package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.TmdbReview
import com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews.MoviePublicReviewsViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TmdbMovieReviews - Composable function that displays TMDB reviews for a movie.
 * Loading indication until loading finished and placeholder text if no reviews available.
 *
 * @author Patch4Code
 */
@Composable
fun TmdbMovieReviews(tmdbMovieReviews: List<TmdbReview>?, tmdbIsLoading: Boolean?, moviePublicReviewsViewModel: MoviePublicReviewsViewModel){

    if (tmdbIsLoading == true){
        LoadingIndicator()
    }else{
        if (tmdbMovieReviews.isNullOrEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.no_tmdb_reviews_text))
            }
            return
        }
        LazyColumn(modifier = Modifier.padding(8.dp)){

            tmdbMovieReviews.forEachIndexed{ index, review->
                item{
                    TmdbReviewItem(review)
                    if(index == tmdbMovieReviews.lastIndex){
                        moviePublicReviewsViewModel.loadMoreTmdbReviews()
                    }
                }
            }
        }
    }
}