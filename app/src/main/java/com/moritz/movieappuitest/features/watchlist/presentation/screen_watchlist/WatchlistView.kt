package com.moritz.movieappuitest.features.watchlist.presentation.screen_watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.core.domain.model.userDataList
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.moritz.movieappuitest.features.watchlist.presentation.components.MovieWatchlistBrowseCard

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