package com.moritz.movieappuitest.views.watchlist


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.MovieHelper

@Composable
fun MovieWatchlistBrowseCard(navController: NavController, movie: Movie) {

    val movieId = movie.id.toString()
    val title = movie.title
    val year = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl = MovieHelper.processPosterUrl(movie.posterUrl)

    Card(modifier = Modifier
        .padding(4.dp)
        .height(220.dp)
        .width(110.dp)
        .clickable {
            navController.navigate(Screen.MovieScreen.withArgs(movieId))
        },
        border = BorderStroke(3.dp, color = Color.DarkGray),
        backgroundColor = Color.DarkGray)
    {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card (modifier = Modifier
                .height(150.dp)
                .width(100.dp),
                backgroundColor = Color.DarkGray
            )
            {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = "$title-Poster"
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = title,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
            Text(
                text = year,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}