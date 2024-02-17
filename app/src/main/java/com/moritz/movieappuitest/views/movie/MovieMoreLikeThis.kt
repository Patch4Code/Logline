package com.moritz.movieappuitest.views.movie

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.views.moviecards.MovieHomeBrowseCard

@Composable
fun MovieMoreLikeThis(navController: NavController, collectionMovies: List<Movie>?, currentMovieTitle: String?){

    if(collectionMovies != null){
        Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
        )
        Text(text = "More like this", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
        LazyRow {
            items(collectionMovies) { movie ->
                if (movie.title != currentMovieTitle) {
                    MovieHomeBrowseCard(navController, movie)
                }
            }
        }
    }
}