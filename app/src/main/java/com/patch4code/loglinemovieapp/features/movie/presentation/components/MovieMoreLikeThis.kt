package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.home.presentation.components.MovieHomeBrowseCard

@Composable
fun MovieMoreLikeThis(navController: NavController, collectionMovies: List<Movie>?, currentMovieTitle: String?){

    if(collectionMovies != null){
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),)
        Text(text = "More like this", modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
        LazyRow {
            items(collectionMovies) { movie ->
                if (movie.title != currentMovieTitle) {
                    MovieHomeBrowseCard(navController, movie)
                }
            }
        }
    }
}