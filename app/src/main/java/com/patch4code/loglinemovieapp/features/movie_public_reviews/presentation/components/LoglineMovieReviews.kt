package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews.MoviePublicReviewsViewModel

@Composable
fun LoglineMovieReviews(moviePublicReviewsViewModel: MoviePublicReviewsViewModel){

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "nothing to see here yet")
    }

}