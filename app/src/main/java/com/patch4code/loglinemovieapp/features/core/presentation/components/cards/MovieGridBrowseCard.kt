package com.patch4code.loglinemovieapp.features.core.presentation.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieGridBrowseCard - Composable function for displaying a movie card for a grid layout.
 *
 * @author Patch4Code
 */
@Composable
fun MovieGridBrowseCard(navController: NavController, movie: Movie?, rating: Int? = null){

    if (movie == null) return

    val movieId = movie.id.toString()
    val title = movie.title
    val year = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl = MovieHelper.processPosterUrl(movie.posterUrl)

    val textColumnHeight = if (rating != null && rating >= 0) 75.dp else 55.dp

    Card(
        modifier = Modifier.padding(4.dp),
        onClick = {navController.navigate(Screen.MovieScreen.withArgs(movieId))}
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.aspectRatio(2/3f),
                model = posterUrl,
                contentDescription = stringResource(R.string.poster_content_description, title),
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )

            Column(
                modifier = Modifier.padding(4.dp).height(textColumnHeight),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 16.sp
                )
                Text(
                    text = year,
                    color = Color.White,
                    fontSize = 11.sp
                )

                // Movie rating if rated
                if(rating != null && rating > 0){
                    Spacer(modifier = Modifier.weight(1f))
                    Row (
                        modifier = Modifier.padding(4.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ){
                        Icon(
                            imageVector = Icons.Default.StarRate,
                            contentDescription = stringResource(id = R.string.star_icon_description),
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(14.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Text(
                            text = "$rating",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}