package com.patch4code.logline.features.home.presentation.screen_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.logline.features.core.presentation.components.cards.MovieRowBrowseCard
import com.patch4code.logline.features.core.presentation.components.load.LoadErrorDisplay
import com.patch4code.logline.features.core.presentation.components.load.LoadingIndicator
import com.patch4code.logline.features.core.presentation.utils.LazyRowStatesSaver
import com.patch4code.logline.features.navigation.domain.model.Screen
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * HomeView - Composable function representing the home screen view.
 *
 * @author Patch4Code
 */
@Composable
fun HomeView(navController: NavController, homeViewModel: HomeViewModel = viewModel()){

    LaunchedEffect(Unit) {
        homeViewModel.loadHomeViewData()
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.HomeScreen.title.asString())

    val isLoading by homeViewModel.isLoading.observeAsState(initial = false)
    val hasLoadError by homeViewModel.hasLoadError.observeAsState(initial = false)
    val homeMoviesMap = homeViewModel.homeMoviesMap.observeAsState().value

    val lazyColumnState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val lazyRowStates = rememberSaveable(saver = LazyRowStatesSaver.saver) { SnapshotStateMap() }

    if(isLoading){
        LoadingIndicator()
    }else if(hasLoadError){
        LoadErrorDisplay(onReload = { homeViewModel.loadHomeViewData() })
    }
    else{
        LazyColumn(state = lazyColumnState) {
            homeMoviesMap?.forEach { (groupName, movies) ->
                item {
                    // row title
                    Text(
                        text = groupName.asString(),
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                    val lazyRowState = lazyRowStates.getOrPut(groupName.asString()) { LazyListState() }
                    // lazy row of movies based on given list
                    LazyRow(state = lazyRowState,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        item { Spacer(Modifier.padding(4.dp)) }
                        itemsIndexed(movies) { index, movie ->
                            MovieRowBrowseCard(navController, movie)

                            // Load more movies when the last item in the row is reached
                            if (index == movies.lastIndex - 1) {
                                homeViewModel.loadMoreMovies(groupName)
                            }
                        }
                        item { Spacer(Modifier.padding(4.dp)) }
                    }
                    Spacer(Modifier.padding(8.dp))
                }
            }
        }
    }
}