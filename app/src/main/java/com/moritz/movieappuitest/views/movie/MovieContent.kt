package com.moritz.movieappuitest.views.movie

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.dataclasses.MovieCredits
import com.moritz.movieappuitest.dataclasses.MovieDetails
import com.moritz.movieappuitest.viewmodels.MovieViewModel
import com.moritz.movieappuitest.views.movie.contentelement.MovieCastAndCrew
import com.moritz.movieappuitest.views.movie.contentelement.MovieDescription
import com.moritz.movieappuitest.views.movie.contentelement.MovieGenres
import com.moritz.movieappuitest.views.movie.contentelement.MovieHeader
import com.moritz.movieappuitest.views.movie.contentelement.MovieMoreDetails
import com.moritz.movieappuitest.views.movie.contentelement.MovieMoreLikeThis
import com.moritz.movieappuitest.views.movie.contentelement.MovieRatings

@Composable
fun MovieContent(
    movieDetails: MovieDetails?,
    movieCredits: MovieCredits?,
    collectionMovies: List<Movie>?,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            MovieHeader(
                id = movieDetails?.id,
                title = movieDetails?.title,
                url = movieDetails?.posterPath,
                releaseDate = movieDetails?.releaseDate,
                runtime = movieDetails?.runtime,
                movieViewModel = movieViewModel
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
}