package com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import java.net.URLEncoder


@Composable
fun PublicReviewItem(publicReview: LoglineReview, navController: NavController){

    Column (modifier = Modifier
        .fillMaxSize()
        .clickable {
            val loglineReviewJson = publicReview.toJson()
            Log.e("PublicReview", "loglineReviewJson: $loglineReviewJson")
            val encodedLoglineReviewJson = URLEncoder.encode(loglineReviewJson, "UTF-8")
            Log.e("PublicReview", "encodedLoglineReviewJson: $encodedLoglineReviewJson")
            navController.navigate(Screen.PublicReviewDetailsScreen.withArgs(encodedLoglineReviewJson))
        }
    ){
        Row (modifier = Modifier.padding(top = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically){
            AsyncImage(
                model = publicReview.avatarPath,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .zIndex(2f)
                    .clickable {  },
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.person_placeholder)
            )
            Text(text = publicReview.authorName, style = MaterialTheme.typography.titleMedium)
            //${publicReview.movie.title}(${MovieHelper.extractYear(publicReview.movie.releaseDate)})

            if(publicReview.rating > 0){
                Spacer(modifier = Modifier.padding(8.dp))
                Row{
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = "StarRate",
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(text = "${publicReview.rating}", style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }

        Row (modifier = Modifier.height(150.dp)){

            Box(modifier = Modifier.weight(1f)) {
                Text(text = publicReview.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            AsyncImage(
                model = MovieHelper.processPosterUrl(publicReview.movie.posterUrl),
                contentDescription = "Poster",
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
    }
}