package com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.cards.MovieGridBrowseCard
import com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog.SortFilterDialog
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.FilterOptionsSaver
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.SortOptionSaver
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarSortFilterActions
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.watchlist.domain.model.WatchlistSortOptions
import com.patch4code.loglinemovieapp.features.watchlist.presentation.components.EmptyWatchlistText
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

    val selectedSortOption: MutableState<SortOption> =
        rememberSaveable(stateSaver = SortOptionSaver.saver) { mutableStateOf(SortOption.ByAddedDesc) }
    val selectedFilterOptions =
        rememberSaveable(stateSaver = FilterOptionsSaver.saver) { mutableStateOf(FilterOptions()) }

    val showFilterDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        watchlistViewModel.loadWatchlistItems(selectedSortOption.value, selectedFilterOptions.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.WatchlistScreen.title.asString())
    ProvideTopBarSortFilterActions(
        sortFilterOnClickAction = {showFilterDialog.value = true},
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
                        userData.movie?.let { MovieGridBrowseCard(navController, it) }
                    }
                }
            }
        )
    }
    SortFilterDialog(showFilterDialog, WatchlistSortOptions.options, selectedSortOption, selectedFilterOptions){
        watchlistViewModel.loadWatchlistItems(selectedSortOption.value, selectedFilterOptions.value)
    }
}