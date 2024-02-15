package com.moritz.movieappuitest.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Genre
import com.moritz.movieappuitest.utils.TmdbCredentials
import com.moritz.movieappuitest.viewmodels.MovieViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.general.ExpandableText
import com.moritz.movieappuitest.views.movie.CastMemberElement
import com.moritz.movieappuitest.views.movie.CrewMemberElement

@Composable
fun MovieView(
    movieViewModel: MovieViewModel = viewModel(),
    navViewModel: NavigationViewModel,
    id: String?
){
    val movieId = id?.toInt() ?: 0
    movieViewModel.loadMovieDetails(movieId)
    val movieDetails = movieViewModel.detailsData.observeAsState().value
    movieViewModel.loadMovieCredits(movieId)
    val movieCredits = movieViewModel.creditsData.observeAsState().value

    val movieTitle = movieDetails?.title ?: "N/A"
    val movieYear: String = movieDetails?.releaseDate.takeIf { !it.isNullOrEmpty() }?.split("-")?.get(0) ?: "N/A"
    val moviePosterUrl: String = (movieDetails?.posterPath.takeIf { !it.isNullOrEmpty() }?.let { TmdbCredentials.POSTER_URL + it }
        ?: "")
    val runtime: String = movieDetails?.runtime.toString()
    val tagline: String = movieDetails?.tagline ?: ""
    val description: String = movieDetails?.overview ?: ""
    val voteAverageTmdb: String = String.format("%.1f", movieDetails?.voteAverage)
    val genres: List<Genre>? = movieDetails?.genres

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MovieScreen)
    }

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                //Poster
                Card (modifier = Modifier
                    .height(200.dp)
                    .width(133.dp),
                    backgroundColor = Color.DarkGray
                )
                {
                    if(moviePosterUrl.isNotEmpty()){
                        AsyncImage(
                            model = moviePosterUrl,
                            contentDescription = "$movieTitle-Poster"
                        )
                    }
                    else{
                        Icon(imageVector = Icons.Default.ImageNotSupported, contentDescription ="$movieTitle-Poster")
                    }
                }
                Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
                    Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "$movieYear | $runtime min", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text(text = tagline, color = Color.White, modifier = Modifier.padding(top = 8.dp), fontWeight = FontWeight.Bold)
            ExpandableText(text =  description, maxLinesCollapsed = 3)

            Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp))
            //Ratings
            Column {
                Text(text = "Average Rating:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    Column {
                        Text(text = voteAverageTmdb, color = Color.White, style = MaterialTheme.typography.titleLarge)
                        Text(text = "TMDB", color = Color.White)
                    }
                }
            }
            Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp))


            //Genre
            Text(text = "Genres:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
            Row {
                genres?.let {
                    Text(text = it.joinToString { genre -> genre.name }, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))

            //Cast + Crew
            Text(
                text = "Cast",
                modifier = Modifier.padding(top = 16.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            LazyRow {
                if (movieCredits != null) {
                    items(movieCredits.cast) { castMember ->
                        CastMemberElement(castMember)
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Crew",
                modifier = Modifier.padding(top = 16.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            LazyRow {
                if (movieCredits != null) {
                    items(movieCredits.crew) { crewMember ->
                        CrewMemberElement(crewMember)
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }
            }

            //More information -> Studio, Country, Budget, Revenue, Status, Spoken Languages

            //More like this
        }
    }
}
