package com.moritz.movieappuitest.userinterface

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

@Composable
fun MovieView(navController: NavController){ //, movieData: DummyMovie

    //(only for test)
    val movieData: DummyMovie? = DummyMovie().getHomeMoviesDummy()["New Movies"]?.firstOrNull()

    val movieTitle = movieData?.title
    val movieYear = movieData?.year.toString()

    Column (modifier = Modifier.padding(16.dp)){
        if (movieTitle != null) {
            Text(text = "$movieTitle ($movieYear)", color = Color.White)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Card (modifier = Modifier
            .height(200.dp)
            .width(133.dp),
            backgroundColor = Color.DarkGray
        )
        {
            if (movieData != null) {
                AsyncImage(
                    model = movieData.posterUrl,
                    contentDescription = "$movieTitle-Poster"
                )
            }
        }
    }

}