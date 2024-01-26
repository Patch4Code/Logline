package com.moritz.movieappuitest.userinterface.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.dataclasses.DummyMovie
import com.moritz.movieappuitest.utils.JSONHelper
import java.net.URLDecoder

@Composable
fun MovieView(navController: NavController, movieString: String?){

    val decodedMovieString = URLDecoder.decode(movieString, "UTF-8")
    val movieData: DummyMovie = JSONHelper.fromJson(decodedMovieString)

    val movieTitle = movieData.title
    val movieYear = movieData.year.toString()

    Column (modifier = Modifier.padding(16.dp)){
        Text(text = "$movieTitle ($movieYear)", color = Color.White)
        Spacer(modifier = Modifier.padding(8.dp))
        Card (modifier = Modifier
            .height(200.dp)
            .width(133.dp),
            backgroundColor = Color.DarkGray
        )
        {
            AsyncImage(
                model = movieData.posterUrl,
                contentDescription = "$movieTitle-Poster"
            )
        }
    }
}