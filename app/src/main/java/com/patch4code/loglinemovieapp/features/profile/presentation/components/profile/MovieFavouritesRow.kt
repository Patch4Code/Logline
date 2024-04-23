package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

@Composable
fun MovieFavouriteRow(navController: NavController, movies: List<Movie>){

    Text(text = stringResource(id = R.string.favourite_movies_title))

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp),
    ){
        movies.forEach {movie->

            val movieId = movie.id
            val moviePosterUrl = TmdbCredentials.POSTER_URL + movie.posterUrl
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable {
                        if (movie.id >= 0){
                            navController.navigate(Screen.MovieScreen.withArgs(movieId.toString()))
                        }
                    },
                model = moviePosterUrl,
                contentDescription = movie.title,
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}