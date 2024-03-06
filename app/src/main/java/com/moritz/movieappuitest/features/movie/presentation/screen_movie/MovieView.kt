package com.moritz.movieappuitest.features.movie.presentation.screen_movie

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.features.movie.presentation.components.MovieContent
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieView(
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel(),
    navViewModel: NavigationViewModel,
    id: String?
){
    val movieId = id?.toIntOrNull() ?: 0

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MovieScreen)
        movieViewModel.loadMovieDetails(movieId)
        movieViewModel.loadMovieCredits(movieId)
        movieViewModel.loadRatingAndWatchlistStatusById(movieId)
    }

    val movieDetails = movieViewModel.detailsData.observeAsState().value
    val movieCredits = movieViewModel.creditsData.observeAsState().value


    DisposableEffect(movieDetails) {
        if (movieDetails != null) {
            val movieCollectionId = movieDetails.collection?.id ?: 0
            movieViewModel.loadMovieCollection(movieCollectionId)
        }
        onDispose {}
    }

    val collectionMovies = movieViewModel.collectionMovies.observeAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ){
        MovieContent(movieDetails, movieCredits, collectionMovies, navController, movieViewModel)
    }
}