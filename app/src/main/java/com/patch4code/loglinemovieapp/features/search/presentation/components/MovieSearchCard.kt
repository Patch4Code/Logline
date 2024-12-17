package com.patch4code.loglinemovieapp.features.search.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
 * MovieSearchCard - Composable function for displaying a movie search item.
 * Consists of a movie-poster, the movie-title and the release-year.
 *
 * @author Patch4Code
 */
@Composable
fun MovieSearchCard(navController: NavController, movie: Movie){

    val movieId = movie.id.toString()
    val title = movie.title
    val year: String = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl: String = MovieHelper.processPosterUrl(movie.posterUrl)

    Column {
        Row (
            modifier = Modifier.height(100.dp).fillMaxWidth()
                .padding(start = 12.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .clickable {
                    navController.navigate(Screen.MovieScreen.withArgs(movieId))
                },

            ){
            AsyncImage(
                model = posterUrl,
                contentDescription = "$title${stringResource(id = R.string.poster_description)}",
                modifier = Modifier.padding(4.dp),
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
            Column {
                Text(
                    text = title,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Text(
                    text = year,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
        HorizontalDivider()
    }
}