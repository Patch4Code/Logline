package com.moritz.movieappuitest.features.search.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper

@Composable
fun MovieSearchCard(navController: NavController, movie: Movie){

    val movieId = movie.id.toString()
    val title = movie.title
    val year: String = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl: String = MovieHelper.processPosterUrl(movie.posterUrl)

    Column {
        Row (
            modifier = Modifier.height(100.dp).fillMaxWidth().padding(8.dp)
                .clickable {
                    navController.navigate(Screen.MovieScreen.withArgs(movieId))
                },

            ){
            AsyncImage(
                model = posterUrl,
                contentDescription = "$title-Poster",
                modifier = Modifier.padding(4.dp),
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
            Column {
                Text(
                    text = title,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 2
                )
                Text(
                    text = year,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.LightGray
                )
            }
        }
        HorizontalDivider()
    }
}