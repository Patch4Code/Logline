package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

@Composable
fun DiscoverContent(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    selectedTabIndex: Int,
    discoverViewModel: DiscoverViewModel = viewModel()
){

    val discoverOptions = remember { mutableStateOf(DiscoverOptions()) }

    val isLoading = discoverViewModel.isLoading.observeAsState().value
    val hasError = discoverViewModel.hasLoadError.observeAsState().value
    val discoveredMovies = discoverViewModel.discoveredMovies.observeAsState().value
    val gridState = rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }

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
            gridState = gridState
        )
    }else{
        DiscoverOptionSelection(
            discoverViewModel = discoverViewModel,
            discoverOptions = discoverOptions
        )
    }
}