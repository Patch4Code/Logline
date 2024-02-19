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
    navController: NavController
) {
    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            MovieHeader(movieDetails?.title, movieDetails?.posterPath, movieDetails?.releaseDate, movieDetails?.runtime)
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