package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.DummyMovie
import com.moritz.movieappuitest.views.moviecards.MovieRatedBrowseCard

@Composable
fun MyMoviesView(navController: NavController){

    //Profile Layout
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            DummyMovie().getRatedDummy().forEach{ movie ->
                item {
                    MovieRatedBrowseCard(navController, movie)
                }
            }
        }
    )
}