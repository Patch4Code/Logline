package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options.DiscoverOptionSelection
import com.patch4code.loglinemovieapp.features.search.presentation.components.discover.results.DiscoveredMoviesContent
import com.patch4code.loglinemovieapp.features.search.presentation.components.utils.DiscoverOptionsSaver
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverContent - Main composable for managing the Discover screen content.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverContent(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    selectedTabIndex: Int,
    discoverViewModel: DiscoverViewModel = viewModel()
){

    val discoverOptions = rememberSaveable(stateSaver = DiscoverOptionsSaver.saver) { mutableStateOf(DiscoverOptions()) }

    val isLoading = discoverViewModel.isLoading.observeAsState().value
    val hasError = discoverViewModel.hasLoadError.observeAsState().value
    val discoveredMovies = discoverViewModel.discoveredMovies.observeAsState().value

    val discoveredMoviesGridState = rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }
    val discoverOptionsListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    if (selectedTabIndex != 1) return

    // searchFocusRequest disabled in this tab
    LaunchedEffect(searchFocusRequest.value) {
        searchFocusRequest.value = false
    }

    if(discoveredMovies != null || isLoading == true || hasError == true){
        DiscoveredMoviesContent(
            discoveredMovies = discoveredMovies,
            isLoading = isLoading,
            hasError = hasError,
            discoverOptions = discoverOptions.value,
            discoverViewModel = discoverViewModel,
            navController = navController,
            gridState = discoveredMoviesGridState
        )
    }else{
        DiscoverOptionSelection(
            discoverViewModel = discoverViewModel,
            discoverOptions = discoverOptions,
            listState = discoverOptionsListState
        )
    }
}