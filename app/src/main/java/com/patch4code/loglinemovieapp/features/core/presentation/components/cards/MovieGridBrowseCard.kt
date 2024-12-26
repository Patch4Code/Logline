package com.patch4code.loglinemovieapp.features.core.presentation.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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

@Composable
fun MovieGridBrowseCard(navController: NavController, movie: Movie?, rating: Int? = null){

    if (movie == null) return

    val movieId = movie.id.toString()
    val title = movie.title
    val year = MovieHelper.extractYear(movie.releaseDate)
    val posterUrl = MovieHelper.processPosterUrl(movie.posterUrl)

    val cardHeight = if (rating != null && rating >= 0) 245.dp else 220.dp

    Card(
        modifier = Modifier .padding(4.dp).height(cardHeight).width(110.dp),
        onClick = {navController.navigate(Screen.MovieScreen.withArgs(movieId))}
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = "$title-Poster",
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        androidx.compose.material.Text(
                            text = "$rating",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}