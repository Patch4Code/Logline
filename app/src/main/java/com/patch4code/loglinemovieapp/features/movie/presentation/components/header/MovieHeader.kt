package com.patch4code.loglinemovieapp.features.movie.presentation.components.header

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.MovieViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun MovieHeader(
    movieDetails: MovieDetails?,
    movieViewModel: MovieViewModel,
    db: LoglineDatabase,
    onPosterClick:() ->Unit
){
    val movieTitle = movieDetails?.title ?: "N/A"
    val moviePosterUrl: String = MovieHelper.processPosterUrl(movieDetails?.posterPath)
    val movieYear = MovieHelper.extractYear(movieDetails?.releaseDate)
    val movieRuntime = movieDetails?.runtime.toString()


    Row{
        //Poster
        Card (modifier = Modifier.height(200.dp).width(133.dp)
            .clickable { onPosterClick() },
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        ) {
            AsyncImage(
                model = moviePosterUrl,
                contentDescription = "$movieTitle${stringResource(id = R.string.poster_description_appendage)}",
                error = painterResource(id = R.drawable.movie_poster_placeholder))
        }
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
            Text(text = movieTitle, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "$movieYear | $movieRuntime min", style = MaterialTheme.typography.bodyMedium)
            MovieHeaderToolbar(movieDetails, movieViewModel, db)
        }
    }
}