package com.moritz.movieappuitest.features.reviews.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie

@Composable
fun ReviewDetailsPoster(loggedElementData: LoggedMovie, onPosterPressed:()->Unit){

    val movieTitle = loggedElementData.movie.title
    val moviePosterUrl: String = MovieHelper.processPosterUrl(loggedElementData.movie.posterUrl)

    Card (modifier = Modifier
        .height(200.dp)
        .width(133.dp)
        .clickable { onPosterPressed() },//navController.navigate(Screen.MovieScreen.withArgs(loggedElementData.movie.id.toString()))
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        AsyncImage(
            model = moviePosterUrl,
            contentDescription = "$movieTitle-Poster",
            error = painterResource(id = R.drawable.movie_poster_placeholder)
        )
    }
}