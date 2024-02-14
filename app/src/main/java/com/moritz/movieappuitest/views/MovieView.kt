package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.utils.TmdbCredentials
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import java.net.URLDecoder

@Composable
fun MovieView(navController: NavController, navViewModel: NavigationViewModel, movieString: String?){

    val decodedMovieString = URLDecoder.decode(movieString, "UTF-8")
    val movieData: Movie = JSONHelper.fromJson(decodedMovieString)

    val movieTitle = movieData.title

    val movieYear: String = movieData.releaseDate.takeIf { !it.isNullOrEmpty() }?.split("-")?.get(0) ?: "N/A"
    val moviePosterUrl: String = (movieData.posterUrl.takeIf { !it.isNullOrEmpty() }?.let { TmdbCredentials.POSTER_URL + it }
        ?: TmdbCredentials.POSTER_PLACEHOLDER_URL)

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MovieScreen)
    }

    Column (modifier = Modifier.padding(16.dp)){
        Row{
            Card (modifier = Modifier
                .height(200.dp)
                .width(133.dp),
                backgroundColor = Color.DarkGray
            )
            {
                AsyncImage(
                    model = moviePosterUrl,
                    contentDescription = "$movieTitle-Poster"
                )
            }
            Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
                Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "$movieYear | 200min", color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        //Text(text = movieData.director, color = Color.White, modifier = Modifier.padding(top = 8.dp))
        //Text(text = "$movieLength min", color = Color.White, modifier = Modifier.padding(top = 8.dp))
        Text(text = movieData.description, color = Color.White, modifier = Modifier.padding(top = 8.dp))
    }
}
