package com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews

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
import androidx.compose.ui.res.stringResource
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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicReviewItem - Composable function for displaying a review item for the PublicReviewsView.
 * Navigates to the PublicReviewDetailsView on click.
 *
 * @author Patch4Code
 */
@Composable
fun PublicReviewItem(publicReview: LoglineReview, navController: NavController){

    val isProfilePublic = publicReview.isProfilePublic
    val userId = publicReview.userId
    val userName = publicReview.authorName

    Column (modifier = Modifier
        .fillMaxSize()
        .clickable {
            val loglineReviewJson = publicReview.toJson()
            val encodedLoglineReviewJson = URLEncoder.encode(loglineReviewJson, "UTF-8")
            navController.navigate(Screen.PublicReviewDetailsScreen.withArgs(encodedLoglineReviewJson))
        }
    ){
        Row (modifier = Modifier.padding(top = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically){
            // user-icon (navigates to PublicProfileView on click) and -name
            AsyncImage(
                model = publicReview.avatarPath,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .zIndex(2f)
                    .clickable(isProfilePublic) {
                        navController.navigate(Screen.PublicProfileScreen.route + "/$userId/$userName") },
                contentScale = ContentScale.Crop,
                error = painterResource(id = if(isProfilePublic) R.drawable.default_profile_image else R.drawable.person_placeholder)
            )
            Text(text = publicReview.authorName, style = MaterialTheme.typography.titleMedium)

            // rating if available
            if(publicReview.rating > 0){
                Spacer(modifier = Modifier.padding(8.dp))
                Row{
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = stringResource(id = R.string.star_icon_description),
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
            // portion of the review text (max up to 7 lines)
            Box(modifier = Modifier.weight(1f)) {
                Text(text = publicReview.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))
            // Movie-poster
            AsyncImage(
                model = MovieHelper.processPosterUrl(publicReview.movie.posterUrl),
                contentDescription = stringResource(id = R.string.poster_description),
                error = painterResource(id = R.drawable.movie_poster_placeholder)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
    }
}