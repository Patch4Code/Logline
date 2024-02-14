package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.viewmodels.HomeViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.general.LoadingIndicator
import com.moritz.movieappuitest.views.moviecards.MovieHomeBrowseCard


@Composable
fun HomeView(navController: NavController, navViewModel: NavigationViewModel,homeViewModel: HomeViewModel = viewModel()){

    val isLoading by homeViewModel.isLoading.observeAsState(initial = false)
    val homeMoviesMap = homeViewModel.homeMoviesMap.observeAsState().value

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.HomeScreen)
    }

    if(isLoading){
        LoadingIndicator()
    }
    else{
        LazyColumn {
            homeMoviesMap?.forEach { (groupName, movies) ->
                item {
                    Text(
                        text = groupName,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    LazyRow {
                        items(movies) { movie ->
                            MovieHomeBrowseCard(navController, movie)
                        }
                    }
                }
            }
        }
    }
}