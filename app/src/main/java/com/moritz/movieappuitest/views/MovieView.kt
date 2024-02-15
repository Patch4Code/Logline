package com.moritz.movieappuitest.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.ProductionCompany
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

    val studios: List<ProductionCompany> = movieDetails?.productionCompanies ?: emptyList()

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            MovieHeader(movieDetails?.title, movieDetails?.posterPath, movieDetails?.releaseDate, movieDetails?.runtime)
            MovieDescription(movieDetails?.tagline, movieDetails?.overview)
            MovieRatings(movieDetails?.voteAverage)
            MovieGenres(movieDetails?.genres)
            MovieCastAndCrew(movieCredits)

            //More information -> Studio, Country, Budget, Revenue, Status, Spoken Languages
            Spacer(modifier = Modifier.padding(16.dp))
            //Details
            var showDetails by remember { mutableStateOf(false) }

            Row (
                modifier = Modifier
                    .animateContentSize(animationSpec = tween(100))
                    .clickable  {
                        showDetails = !showDetails
                    }
            ){
                Text(text = "More Details", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(100.dp))
                Icon(
                    imageVector = if(showDetails)Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "ArrowDown"
                )
            }
            Column (modifier = Modifier.padding(top = 8.dp)){
                if(showDetails){
                    //Production Companies

                    Text(text = "Production Companies:")
                    Row {
                        studios?.let {
                            Text(text = it.joinToString { studio -> studio.name }, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))


                    //Country


                    //Budget

                    //Revenue

                    //Status

                    //Spoken Languages
                }
            }





            //More like this
        }
    }
}
