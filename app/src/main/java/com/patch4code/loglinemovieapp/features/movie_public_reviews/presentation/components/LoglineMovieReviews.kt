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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview

@Composable
fun LoglineMovieReviews(loglineMovieReviews: List<LoglineReview>?, loglineIsLoading: Boolean?, navController: NavController){

    if (loglineIsLoading == true){
        LoadingIndicator()
    }else{
        if (loglineMovieReviews.isNullOrEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "no Logline reviews yet")
            }
            return
        }

        LazyColumn(modifier = Modifier.padding(8.dp)){

            loglineMovieReviews.forEach{ review->
                item{
                    LoglineReviewItem(review, navController)
                }
            }
        }
    }
}