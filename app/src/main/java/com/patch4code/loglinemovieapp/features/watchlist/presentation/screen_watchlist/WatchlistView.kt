package com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.watchlist.presentation.components.MovieWatchlistBrowseCard
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * WatchlistView - Composable function representing the watchlist screen view.
 * Shows all movies that are on the watchlist.
 *
 * @author Patch4Code
 */
@Composable
fun WatchlistView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    watchlistViewModel: WatchlistViewModel = viewModel(
        factory = WatchlistViewModelFactory(db.movieUserDataDao)
    )
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.WatchlistScreen)
        watchlistViewModel.setUserdataList()
    }

    val userDataList = watchlistViewModel.myUserDataList.observeAsState().value

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            val watchlistItems = userDataList?.filter {it.onWatchlist}
            watchlistItems?.forEach{ userData ->
                item {
                    if(userData.onWatchlist){
                        userData.movie?.let { MovieWatchlistBrowseCard(navController, it) }
                    }
                }
            }
        }
    )
}