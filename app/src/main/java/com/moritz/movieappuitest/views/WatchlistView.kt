package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.userDataList
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.moviecards.MovieWatchlistBrowseCard

@Composable
fun WatchlistView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.WatchlistScreen)
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            val watchlistItems = userDataList.filter {it.onWatchlist}
            watchlistItems.forEach{ userData ->
                item {
                    if(userData.onWatchlist){
                        userData.movie?.let { MovieWatchlistBrowseCard(navController, it) }
                    }
                }
            }
        }
    )
}