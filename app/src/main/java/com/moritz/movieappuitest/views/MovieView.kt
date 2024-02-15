package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.viewmodels.MovieViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.movie.MovieCastAndCrew
import com.moritz.movieappuitest.views.movie.MovieDescription
import com.moritz.movieappuitest.views.movie.MovieGenres
import com.moritz.movieappuitest.views.movie.MovieHeader
import com.moritz.movieappuitest.views.movie.MovieRatings

@Composable
fun MovieView(
    movieViewModel: MovieViewModel = viewModel(),
    navViewModel: NavigationViewModel,
    id: String?
){
    val movieId = id?.toIntOrNull() ?: 0

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MovieScreen)
        movieViewModel.loadMovieDetails(movieId)
        movieViewModel.loadMovieCredits(movieId)
    }
    val movieDetails = movieViewModel.detailsData.observeAsState().value
    val movieCredits = movieViewModel.creditsData.observeAsState().value

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            MovieHeader(movieDetails?.title, movieDetails?.posterPath, movieDetails?.releaseDate, movieDetails?.runtime)
            MovieDescription(movieDetails?.tagline, movieDetails?.overview)
            MovieRatings(movieDetails?.voteAverage)
            MovieGenres(movieDetails?.genres)
            MovieCastAndCrew(movieCredits)

            //More information -> Studio, Country, Budget, Revenue, Status, Spoken Languages

            //More like this
        }
    }
}
