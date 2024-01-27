package com.moritz.movieappuitest.userinterface.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.dataclasses.DummyMovie
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.viewmodels.updateScreenTitle
import java.net.URLDecoder

@Composable
fun MovieView(navController: NavController, navViewModel: NavigationViewModel, movieString: String?){

    val decodedMovieString = URLDecoder.decode(movieString, "UTF-8")
    val movieData: DummyMovie = JSONHelper.fromJson(decodedMovieString)

    val movieTitle = movieData.title
    val movieYear = movieData.year.toString()
    val movieLength = movieData.length.toString()

    LaunchedEffect(movieTitle) {
        updateScreenTitle(navViewModel, "$movieTitle ($movieYear)")
    }
    Column (modifier = Modifier.padding(16.dp)){
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
        Text(text = movieData.director, color = Color.White, modifier = Modifier.padding(top = 8.dp))
        Text(text = "$movieLength min", color = Color.White, modifier = Modifier.padding(top = 8.dp))
        Text(text = movieData.description, color = Color.White, modifier = Modifier.padding(top = 8.dp))
    }
}