package com.moritz.movieappuitest.views.moviecards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.DummyMovie
import com.moritz.movieappuitest.utils.JSONHelper.toJson
import java.net.URLEncoder

@Composable
fun MovieBrowseCard(navController: NavController, movie: DummyMovie) {

    val title = movie.title
    val year = movie.year
    val posterUrl = movie.posterUrl

    val jsonMovie = movie.toJson()
    val encodedJsonMovie = URLEncoder.encode(jsonMovie, "UTF-8")

    Card(modifier = Modifier
        .padding(16.dp)
        .height(260.dp)
        .width(133.dp)
        .clickable {
            navController.navigate(Screen.MovieScreen.withArgs(encodedJsonMovie))
        },
        border = BorderStroke(3.dp, color = Color.DarkGray),
        backgroundColor = Color.DarkGray)
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card (modifier = Modifier
                .height(200.dp)
                .width(133.dp),
                backgroundColor = Color.DarkGray
            )
            {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = "$title-Poster"
                )
            }
            Text(
                text = title,
                color = Color.White,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
            Text(
                text = year.toString(),
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}