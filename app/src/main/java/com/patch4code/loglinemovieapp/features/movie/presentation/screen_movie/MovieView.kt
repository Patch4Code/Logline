package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

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
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.movie.presentation.components.MovieContent
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import java.net.URLEncoder


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
            FloatingActionButton(onClick = {
                val movieToLog = Movie(
                    title = movieDetails?.title.toString(),
                    id=movieId,
                    releaseDate = movieDetails?.releaseDate.toString(),
                    posterUrl = movieDetails?.posterPath.toString()
                )
                val jsonMovie = movieToLog.toJson()
                val encodedJsonMovie = URLEncoder.encode(jsonMovie, "UTF-8")
                navController.navigate(Screen.MovieLogScreen.withArgs(encodedJsonMovie))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ){
        MovieContent(movieDetails, movieCredits, collectionMovies, navController, movieViewModel)
    }
}