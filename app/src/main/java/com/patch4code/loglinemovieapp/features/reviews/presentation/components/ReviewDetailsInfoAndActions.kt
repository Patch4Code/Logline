package com.patch4code.loglinemovieapp.features.reviews.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewDetailsViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

@Composable
fun ReviewDetailsInfoAndActions(
    reviewedLog: LoggedMovie,
    navController: NavController,
    reviewDetailsViewModel: ReviewDetailsViewModel
){

    val movieTitle = reviewedLog.movie.title
    val movieYear = MovieHelper.extractYear(reviewedLog.movie.releaseDate)
    val rating = reviewedLog.rating.toString()
    val formatedDate = MovieHelper.formatDate(reviewedLog.date)

    val comingFromDiaryView = navController.previousBackStackEntry?.destination?.route == Screen.DiaryScreen.route

    val context = LocalContext.current
    val dataLoginStore = remember { StoreUserData(context) }
    val savedLoginData = dataLoginStore.getUserId.collectAsState(initial = "")
    val openMakeReviewPublicDialog = remember { mutableStateOf(false)  }

    Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
        Text(text = movieTitle, style = MaterialTheme.typography.titleMedium, maxLines = 2)
        Text(text = movieYear, style = MaterialTheme.typography.bodyMedium)

        if(reviewedLog.rating > 0){
            Row (
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Text(text = rating, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(
                    Alignment.CenterVertically))
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = stringResource(id = R.string.star_icon_description),
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Text(text = "${formatedDate[0]}. ${formatedDate[1]} ${formatedDate[2]}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))

        
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = { navController.navigate("${Screen.DiaryEditElementScreen.route}/${reviewedLog.id}/$comingFromDiaryView") }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.edit_review_icon_description))
            }
            IconButton(enabled = savedLoginData.value?.isNotEmpty() == true,
                onClick = {openMakeReviewPublicDialog.value = true}) {
                Icon(imageVector = Icons.Default.Public, contentDescription = stringResource(id = R.string.publish_review_icon_description))
            }
        }
    }

    MakeReviewPublicDialog(openMakeReviewPublicDialog, reviewDetailsViewModel)
}