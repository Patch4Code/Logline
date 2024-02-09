package com.moritz.movieappuitest.views.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.utils.JSONHelper.toJson
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.TmdbCredentials
import java.net.URLEncoder

@Composable
fun MovieFavouriteRow(navController: NavController, movies: List<Movie>){

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp),
    ){
        movies.forEach {movie->
            val jsonMovie = movie.toJson()
            val encodedJsonMovie = URLEncoder.encode(jsonMovie, "UTF-8")

            val moviePosterUrl = TmdbCredentials.POSTER_URL + movie.posterUrl
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { navController.navigate(Screen.MovieScreen.withArgs(encodedJsonMovie)) },
                model = moviePosterUrl,
                contentDescription = movie.title
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}