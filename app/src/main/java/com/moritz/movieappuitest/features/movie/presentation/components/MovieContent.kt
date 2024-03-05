package com.moritz.movieappuitest.features.movie.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.movie.domain.model.MovieCredits
import com.moritz.movieappuitest.features.movie.domain.model.MovieDetails
import com.moritz.movieappuitest.features.movie.presentation.components.cast_and_crew.MovieCastAndCrew
import com.moritz.movieappuitest.features.movie.presentation.components.header.MovieHeader
import com.moritz.movieappuitest.features.movie.presentation.screen_movie.MovieViewModel

@Composable
fun MovieContent(
    movieDetails: MovieDetails?,
    movieCredits: MovieCredits?,
    collectionMovies: List<Movie>?,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    val openPosterPopup = remember { mutableStateOf(false)  }

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            MovieHeader(
                movieDetails = movieDetails,
                movieViewModel = movieViewModel,
                onPosterClick = { openPosterPopup.value = true }
            )
            MovieDescription(movieDetails?.tagline, movieDetails?.overview)
            MovieRatings(movieDetails?.voteAverage)
            MovieGenres(movieDetails?.genres)
            MovieCastAndCrew(movieCredits)
            MovieMoreDetails(
                movieDetails?.productionCompanies,
                movieDetails?.productionCountries,
                movieDetails?.budget,
                movieDetails?.revenue,
                movieDetails?.status,
                movieDetails?.spokenLanguages
            )
            MovieMoreLikeThis(navController, collectionMovies, movieDetails?.title)
        }
    }
    MoviePosterPopup(openPosterPopup.value, movieDetails, onPosterPopupClose = {openPosterPopup.value = false})
}