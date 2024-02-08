package com.moritz.movieappuitest.views

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.viewmodels.HomeViewModel
import com.moritz.movieappuitest.views.moviecards.MovieHomeBrowseCard


@Composable
fun HomeView(navController: NavController, homeViewModel: HomeViewModel = viewModel()){

    val homeMoviesMap  = homeViewModel.homeMoviesMap.observeAsState().value
    //Log.e("Home map", homeMoviesMap.toString())

    LazyColumn {
        Log.e("HomeView map", homeMoviesMap.toString())
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