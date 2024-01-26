package com.moritz.movieappuitest.userinterface

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.DummyMovie

@Composable
fun MainView(navController: NavController){

    LazyColumn {
        DummyMovie().getHomeMoviesDummy().forEach{ (groupName, movies) ->
            item {
                Text(
                    text = groupName,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
                LazyRow {
                    items(movies){
                            movie->
                        MovieBrowseCard(title = movie.title, year = movie.year, posterUrl = movie.posterUrl)
                    }
                }
            }
        }
    }
}