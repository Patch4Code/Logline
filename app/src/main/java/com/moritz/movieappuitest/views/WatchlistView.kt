package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.getWatchlistDummy
import com.moritz.movieappuitest.views.moviecards.MovieWatchlistBrowseCard

@Composable
fun WatchlistView(navController: NavController){

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            getWatchlistDummy().forEach{ movie ->
                item {
                    MovieWatchlistBrowseCard(navController, movie)
                }
            }
        }
    )
}