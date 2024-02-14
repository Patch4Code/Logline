package com.moritz.movieappuitest.views

import android.util.Log
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
import androidx.compose.material.Text
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.utils.TmdbCredentials
import com.moritz.movieappuitest.viewmodels.MovieViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.general.ExpandableText
import com.moritz.movieappuitest.views.movie.CastMemberElement
import com.moritz.movieappuitest.views.movie.CrewMemberElement
import java.net.URLDecoder

@Composable
fun MovieView(
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel(),
    navViewModel: NavigationViewModel,
    movieString: String?
){
    val decodedMovieString = URLDecoder.decode(movieString, "UTF-8")
    val movieData: Movie = JSONHelper.fromJson(decodedMovieString)

    movieViewModel.getMovieCredits(movieData.id)
    val movieCredits = movieViewModel.creditsData.observeAsState().value

    val movieTitle = movieData.title

    val movieYear: String = movieData.releaseDate.takeIf { !it.isNullOrEmpty() }?.split("-")?.get(0) ?: "N/A"
    val moviePosterUrl: String = (movieData.posterUrl.takeIf { !it.isNullOrEmpty() }?.let { TmdbCredentials.POSTER_URL + it }
        ?: TmdbCredentials.POSTER_PLACEHOLDER_URL)

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MovieScreen)
    }

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                Card (modifier = Modifier
                    .height(200.dp)
                    .width(133.dp),
                    backgroundColor = Color.DarkGray
                )
                {
                    AsyncImage(
                        model = moviePosterUrl,
                        contentDescription = "$movieTitle-Poster"
                    )
                }
                Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
                    Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "$movieYear | 200min", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text(text = "Bla Bla Bla", color = Color.White, modifier = Modifier.padding(top = 8.dp))
            ExpandableText(text =  movieData.description, maxLinesCollapsed = 3)

            Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp))
            //Ratings
            Column {
                Text(text = "Average Rating:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    Column {
                        Text(text = "6.8", color = Color.White, style = MaterialTheme.typography.titleLarge)
                        Text(text = "TMDB", color = Color.White)
                    }
                }
            }
            Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp))


            //Genre
            Text(text = "Genres:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
            Text(text = "Action, Comedy, Horror", color = Color.White)
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
                        Log.e("Cast", castMember.toString())
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

            //More Infos -> Studio, Countrie, Budget,

            //More like this
        }

    }
}
