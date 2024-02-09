package com.moritz.movieappuitest.views.moviecards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.JSONHelper.toJson
import com.moritz.movieappuitest.utils.TmdbCredentials
import java.net.URLEncoder

@Composable
fun MovieSearchCard(navController: NavController, movie: Movie){

    val title = movie.title
    val year = movie.releaseDate.split("-")[0]
    val posterUrl = TmdbCredentials.POSTER_URL + movie.posterUrl

    val jsonMovie = movie.toJson()
    val encodedJsonMovie = URLEncoder.encode(jsonMovie, "UTF-8")

    Column {
        Row (
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    navController.navigate(Screen.MovieScreen.withArgs(encodedJsonMovie))
                },

            ){
            AsyncImage(
                model = posterUrl,
                contentDescription = "$title-Poster",
                modifier = Modifier.padding(4.dp)
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
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}