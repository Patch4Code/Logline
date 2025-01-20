package com.patch4code.loglinemovieapp.features.list.presentation.components.list.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListItem - Composable function represents a single item in a list of movies.
 * Navigates to MovieView on click
 *
 * @author Patch4Code
 */
@Composable
fun ListItem(navController: NavController, movie: Movie, modifier: Modifier){

    val movieId = movie.id.toString()
    val movieTitle = movie.title
    val movieYear = MovieHelper.extractYear(movie.releaseDate)
    val moviePosterUrl = MovieHelper.processPosterUrl(movie.posterUrl)

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(8.dp)
                .clickable {
                    navController.navigate(Screen.MovieScreen.withArgs(movieId))
                }
        ){
            AsyncImage(
                model = moviePosterUrl,
                contentDescription = "${movieTitle}${stringResource(id = R.string.poster_description_appendage)}",
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
            Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp).width(140.dp))
            {
                Text(text = movieTitle, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movieYear, style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}