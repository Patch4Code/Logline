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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.ExpandableText
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoglineReviewItem - Composable function that displays a single logline review item.
 * Consists of clickable user-icon (leads to public user profile), username, rating and review-text.
 *
 * @author Patch4Code
 */
@Composable
fun LoglineReviewItem(loglineReview: LoglineReview, navController: NavController){

    val isProfilePublic = loglineReview.isProfilePublic
    val userId = loglineReview.userId
    val userName = loglineReview.authorName

    Column {
        Row (modifier = Modifier, verticalAlignment = Alignment.CenterVertically){
            // users avatar image (clickable leads to PublicProfileScreen)
            AsyncImage(
                model = loglineReview.avatarPath,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .zIndex(2f)
                    .clickable (isProfilePublic){
                        navController.navigate(Screen.PublicProfileScreen.route + "/$userId/$userName") },
                contentScale = ContentScale.Crop,
                error = painterResource(id = if(isProfilePublic) R.drawable.default_profile_image else R.drawable.person_placeholder)

            )
            // username
            Text(text = loglineReview.authorName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(4.dp))
            // rating if available
            if ((loglineReview.rating != null) && (loglineReview.rating > 0)){
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = stringResource(id = R.string.star_icon_description),
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = loglineReview.rating.toString())
            }
        }
        // review-text (expandable)
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp)){
            ExpandableText(text =  loglineReview.content, maxLinesCollapsed = 8)
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}