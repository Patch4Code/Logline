package com.moritz.movieappuitest.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.dataclasses.DummyMovie
import com.moritz.movieappuitest.utils.TmdbCredentials
import com.moritz.movieappuitest.viewmodels.HomeViewModel
import com.moritz.movieappuitest.views.moviecards.MovieHomeBrowseCard

@Composable
fun HomeView(navController: NavController, homeViewModel: HomeViewModel = viewModel()){

    LaunchedEffect(homeViewModel) {
        homeViewModel.loadPopularMovies()
    }
    val popularMovies  = homeViewModel.popularMovies


    LazyColumn {
        DummyMovie().getHomeMoviesDummy().forEach { (groupName, movies) ->
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
        item{

            Text(
                text = "Popular Movies",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            LazyRow {
                items(items = popularMovies.value ?: emptyList()){ movie ->
                    Card(modifier = Modifier
                        .padding(16.dp)
                        .height(260.dp)
                        .width(133.dp),
                        border = BorderStroke(3.dp, color = Color.DarkGray),
                        backgroundColor = Color.DarkGray)
                    {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card (modifier = Modifier
                                .height(200.dp)
                                .width(133.dp),
                                backgroundColor = Color.DarkGray
                            )
                            {
                                AsyncImage(
                                    model = TmdbCredentials.POSTER_URL + movie.posterPath,
                                    contentDescription = "${movie.title}-Poster"
                                )
                            }
                            Text(
                                text = movie.title,
                                color = Color.White,
                                maxLines = 2,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                            )
                            Text(
                                text = movie.releaseDate.split("-")[0],
                                color = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}