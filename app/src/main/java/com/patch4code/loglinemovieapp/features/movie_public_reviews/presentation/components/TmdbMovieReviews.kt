package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.Review
import com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews.MoviePublicReviewsViewModel

@Composable
fun TmdbMovieReviews(tmdbMovieReviews: List<Review>?, moviePublicReviewsViewModel: MoviePublicReviewsViewModel){

    LazyColumn(modifier = Modifier.padding(8.dp)){

        tmdbMovieReviews?.forEachIndexed{index, review->
            item{
                TmdbReviewItem(review)
                if(index == tmdbMovieReviews.lastIndex){
                    moviePublicReviewsViewModel.loadMoreTmdbReviews()
                }
            }
        }
    }
}