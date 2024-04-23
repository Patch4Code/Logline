package com.patch4code.loglinemovieapp.features.reviews.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewItem(loggedItem: LoggedMovie, navController: NavController){

    val loggedItemId = loggedItem.id

    Column (modifier = Modifier
        .fillMaxSize()
        .clickable { navController.navigate(Screen.ReviewDetailScreen.withArgs(loggedItemId)) }
    ){

        FlowRow (modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        ){
            Text(
                text = "${loggedItem.movie.title} (${MovieHelper.extractYear(loggedItem.movie.releaseDate)})",
                style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(start = 8.dp))

            if(loggedItem.rating > 0){
                Row{
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = stringResource(id = R.string.star_icon_description),
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(text = "${loggedItem.rating}", style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }

        Row (modifier = Modifier.height(150.dp)){
            AsyncImage(
                model = MovieHelper.processPosterUrl(loggedItem.movie.posterUrl),
                contentDescription = stringResource(id = R.string.poster_description),
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = loggedItem.review,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 7,
                overflow = TextOverflow.Ellipsis
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
    }
}