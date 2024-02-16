package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.viewmodels.MovieViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.movie.MovieCastAndCrew
import com.moritz.movieappuitest.views.movie.MovieDescription
import com.moritz.movieappuitest.views.movie.MovieGenres
import com.moritz.movieappuitest.views.movie.MovieHeader
import com.moritz.movieappuitest.views.movie.MovieMoreDetails
import com.moritz.movieappuitest.views.movie.MovieRatings
import com.moritz.movieappuitest.views.moviecards.MovieHomeBrowseCard

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
    }
    val movieDetails = movieViewModel.detailsData.observeAsState().value
    val movieCredits = movieViewModel.creditsData.observeAsState().value

    DisposableEffect(movieDetails) {
        // Warte, bis movieDetails nicht null ist, bevor die Movie Collection geladen wird
        if (movieDetails != null) {
            val movieCollectionId = movieDetails.collection?.id ?: 0
            movieViewModel.loadMovieCollection(movieCollectionId)
        }
        onDispose {}
    }
    val collectionMovies = movieViewModel.collectionMovies.observeAsState().value


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
            //More like this
            if(collectionMovies != null){
                Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                )
                Text(text = "More like this", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                LazyRow {
                    items(collectionMovies) { movie ->
                        if (movie.title != movieDetails?.title) {
                            MovieHomeBrowseCard(navController, movie)
                        }
                    }

                }
            }
        }
    }
}
