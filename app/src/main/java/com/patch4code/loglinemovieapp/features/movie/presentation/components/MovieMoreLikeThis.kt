package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.home.presentation.components.MovieHomeBrowseCard

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieMoreLikeThis - Composable function that displays movies similar to the current movie.
 *
 * @author Patch4Code
 */
@Composable
fun MovieMoreLikeThis(navController: NavController, collectionMovies: List<Movie>?, currentMovieTitle: String?){

    if(collectionMovies != null){
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
        Text(text = stringResource(id = R.string.more_like_this_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
        LazyRow {
            items(collectionMovies) { movie ->
                if (movie.title != currentMovieTitle) {
                    MovieHomeBrowseCard(navController, movie)
                }
            }
        }
    }
}