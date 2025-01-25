package com.patch4code.logline.features.core.presentation.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.presentation.utils.MovieHelper
import com.patch4code.logline.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieRowBrowseCard - Composable function for displaying a movie card for a row.
 *
 * @author Patch4Code
 */

@Composable
fun MovieRowBrowseCard(navController: NavController, movie: Movie){

    val movieId = movie.id.toString()
    val title = movie.title
    val year = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl = MovieHelper.processPosterUrl(movie.posterUrl)

    Card(
        modifier = Modifier.height(226.dp).width(110.dp),
        onClick = {navController.navigate(Screen.MovieScreen.withArgs(movieId))}
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = stringResource(R.string.poster_content_description, title),
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )

            Box(modifier = Modifier.fillMaxSize().padding(4.dp), contentAlignment = Alignment.TopCenter) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = title,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        lineHeight = 16.sp
                    )
                    Text(
                        text = year,
                        color = Color.White,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}