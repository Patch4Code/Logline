package com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarSortActionsAndFilter
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.watchlist.domain.model.WatchlistSortOption
import com.patch4code.loglinemovieapp.features.watchlist.presentation.components.EmptyWatchlistText
import com.patch4code.loglinemovieapp.features.watchlist.presentation.components.MovieWatchlistBrowseCard
import com.patch4code.loglinemovieapp.features.watchlist.presentation.components.WatchlistFilterDialog
import com.patch4code.loglinemovieapp.features.watchlist.presentation.components.WatchlistSortBottomSheet
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
    db: LoglineDatabase,
    watchlistViewModel: WatchlistViewModel = viewModel(
        factory = WatchlistViewModelFactory(db.movieUserDataDao)
    )
){

    val selectedSortOption = remember { mutableStateOf(WatchlistSortOption.ByAddedDesc) }
    val selectedFilterOptions = remember { mutableStateOf(FilterOptions()) }

    val showBottomSheet = remember { mutableStateOf(false)  }
    val showFilterDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        watchlistViewModel.loadWatchlistItems(selectedSortOption.value, selectedFilterOptions.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.WatchlistScreen.title.asString())
    ProvideTopBarSortActionsAndFilter(
        sortOnClickAction = {showBottomSheet.value = true},
        filterOnClickAction = {showFilterDialog.value = true},
    )

    val watchlistItems = watchlistViewModel.watchlistItems.observeAsState().value

    if (watchlistItems.isNullOrEmpty()){
        EmptyWatchlistText(FilterHelper.isAnyFilterApplied(selectedFilterOptions.value))
    }else{
        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            columns = GridCells.Fixed(3),
            content = {
                watchlistItems.forEach{ userData ->
                    item {
                        userData.movie?.let { MovieWatchlistBrowseCard(navController, it) }
                    }
                }
            }
        )
    }

    WatchlistSortBottomSheet(showBottomSheet, selectedSortOption){
        watchlistViewModel.loadWatchlistItems(selectedSortOption.value, selectedFilterOptions.value)
    }
    WatchlistFilterDialog(showFilterDialog, selectedFilterOptions){
        watchlistViewModel.loadWatchlistItems(selectedSortOption.value, selectedFilterOptions.value)
    }
}