package com.patch4code.loglinemovieapp.features.social.presentation.components.public_profile

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import java.net.URLEncoder

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PublicProfileReviewItem(loglineReview: LoglineReview, navController: NavController){

    Column (modifier = Modifier
        .fillMaxSize()
        .clickable {
            val loglineReviewJson = loglineReview.toJson()
            val encodedLoglineReviewJson = URLEncoder.encode(loglineReviewJson, "UTF-8")
            navController.navigate(Screen.PublicReviewDetailsScreen.withArgs(encodedLoglineReviewJson))
        }
    ){

        FlowRow (modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        ){
            Text(
                text = "${loglineReview.movie.title} (${MovieHelper.extractYear(loglineReview.movie.releaseDate)})",
                style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(start = 8.dp))

            if(loglineReview.rating > 0){
                Row{
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = "StarRate",
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(text = "${loglineReview.rating}", style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }

        Row (modifier = Modifier.height(150.dp)){
            AsyncImage(
                model = MovieHelper.processPosterUrl(loglineReview.movie.posterUrl),
                contentDescription = "Poster",
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = loglineReview.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 7,
                overflow = TextOverflow.Ellipsis
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
    }
}