package com.patch4code.loglinemovieapp.features.home.presentation.screen_home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.load.LoadErrorDisplay
import com.patch4code.loglinemovieapp.features.core.presentation.components.load.LoadingIndicator
import com.patch4code.loglinemovieapp.features.home.presentation.components.MovieHomeBrowseCard
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle

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

    if(isLoading){
        LoadingIndicator()
    }else if(hasLoadError){
        LoadErrorDisplay(onReload = { homeViewModel.loadHomeViewData() })
    }
    else{
        LazyColumn {
            homeMoviesMap?.forEach { (groupName, movies) ->
                item {
                    // row title
                    Text(text = groupName.asString(),
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        fontWeight = FontWeight.Bold)
                    // lazy row of movies based on given list
                    LazyRow {
                        itemsIndexed(movies) { index, movie ->
                            MovieHomeBrowseCard(navController, movie)

                            // Load more movies when the last item in the row is reached
                            if (index == movies.lastIndex - 1) {
                                homeViewModel.loadMoreMovies(groupName)
                            }
                        }
                    }
                }
            }
        }
    }
}