package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.ExpandableText
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.TmdbReview

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TmdbReviewItem - Composable function that displays a single TMDB review item.
 * Consists of clickable user-icon (opens link to review on TMDB website),
 * username, rating and review-text.
 *
 * @author Patch4Code
 */
@Composable
fun TmdbReviewItem(tmdbReview: TmdbReview){

    val uriHandler = LocalUriHandler.current

    Column {
        Row (modifier = Modifier, verticalAlignment = Alignment.CenterVertically){
            // users avatar image (clickable opens link to review on TMDB website)
            AsyncImage(
                model = TmdbCredentials.OTHER_IMAGE_URL + tmdbReview.tmdbAuthorDetails.avatarPath,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { uriHandler.openUri(tmdbReview.url) },
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.person_placeholder)

            )
            // username
            Text(text = tmdbReview.author, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(4.dp))
            // rating if available
            if ((tmdbReview.tmdbAuthorDetails.rating != null) && (tmdbReview.tmdbAuthorDetails.rating > 0)){
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = stringResource(id = R.string.star_icon_description),
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = tmdbReview.tmdbAuthorDetails.rating.toString())
            }
        }
        // review-text (expandable)
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp)){
            ExpandableText(text =  tmdbReview.content, maxLinesCollapsed = 8)
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}