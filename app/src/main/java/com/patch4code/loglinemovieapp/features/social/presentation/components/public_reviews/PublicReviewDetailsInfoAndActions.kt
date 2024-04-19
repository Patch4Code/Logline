package com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews.PublicReviewDetailsViewModel

@Composable
fun PublicReviewDetailsInfoAndActions(
    review: LoglineReview,
    navController: NavController,
    isYourReview: Boolean?,
    publicReviewDetailsViewModel: PublicReviewDetailsViewModel
){
    val isProfilePublic = review.isProfilePublic
    val userId = review.userId
    val userName = review.authorName

    val movieYear = MovieHelper.extractYear(review.movie.releaseDate)
    val rating = review.rating
    val formatedDate = MovieHelper.formatDate(review.createdAt)

    val showDeletePublicReviewDialog = remember { mutableStateOf(false) }

    Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){

        Row (modifier = Modifier.padding(bottom = 4.dp), verticalAlignment = Alignment.CenterVertically){
            AsyncImage(
                model = review.avatarPath,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .zIndex(2f)
                    .clickable(isProfilePublic) {
                        navController.navigate(Screen.PublicProfileScreen.route + "/$userId/$userName") },
                contentScale = ContentScale.Crop,
                error = painterResource(id = if(isProfilePublic) R.drawable.default_profile_image else R.drawable.person_placeholder)
            )
            Text(text = review.authorName, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }


        Text(text = review.movie.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = movieYear, style = MaterialTheme.typography.bodyMedium)

        if(rating > 0){
            Row (
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Text(text = "$rating", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(
                    Alignment.CenterVertically))
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "StarRate",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Text(text = "${formatedDate[0]}. ${formatedDate[1]} ${formatedDate[2]}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(4.dp))

        if(isYourReview == true){
            IconButton(onClick = { showDeletePublicReviewDialog.value = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Public Review")
            }
        }
    }

    DeletePublicReviewDialog(showDeletePublicReviewDialog, review.objectId,navController, publicReviewDetailsViewModel)
}