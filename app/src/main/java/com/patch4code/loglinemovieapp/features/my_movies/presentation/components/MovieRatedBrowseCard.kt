package com.patch4code.loglinemovieapp.features.my_movies.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieRatedBrowseCard - Composable function for displaying a card representing a rated movie.
 * Card with movie details such as title, release year, poster image, and rating.
 *
 * @author Patch4Code
 */
@Composable
fun MovieRatedBrowseCard(navController: NavController, movieUserData: MovieUserData) {

    val movie: Movie = movieUserData.movie ?: Movie()

    val movieId = movie.id.toString()
    val title = movie.title
    val year = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl = MovieHelper.processPosterUrl(movie.posterUrl)

    val rating = movieUserData.rating

    Card(modifier = Modifier.padding(4.dp).height(240.dp).width(120.dp)
        .clickable {
            navController.navigate(Screen.MovieScreen.withArgs(movieId))
        },
        border = BorderStroke(3.dp, color = Color.DarkGray),
        backgroundColor = Color.DarkGray)
    {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // poster image
            Card (modifier = Modifier.height(150.dp).width(100.dp),
                backgroundColor = Color.DarkGray
            ) {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = "$title${stringResource(id = R.string.poster_description_appendage)}",
                    error = painterResource(id = R.drawable.movie_poster_placeholder)
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            // Movie title
            Text(
                text = title,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
            // Movie release year
            Text(
                text = year,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            // Movie rating if rated
            if(rating > 0){
                Row (
                    modifier = Modifier.padding(4.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ){
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = stringResource(id = R.string.star_icon_description),
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "$rating",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}