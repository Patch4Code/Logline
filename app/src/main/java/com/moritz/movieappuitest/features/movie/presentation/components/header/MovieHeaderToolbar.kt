package com.moritz.movieappuitest.features.movie.presentation.components.header

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.features.core.presentation.components.DiaryEditRatingDialog
import com.moritz.movieappuitest.features.movie.domain.model.MovieDetails
import com.moritz.movieappuitest.features.movie.presentation.components.dialogs.AddToListDialog
import com.moritz.movieappuitest.features.movie.presentation.screen_movie.MovieViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieHeaderToolbar(movieDetails: MovieDetails?, movieViewModel: MovieViewModel){

    val id = movieDetails?.id

    val openRatingDialog = remember { mutableStateOf(false)  }
    val openAddToListDialog = remember { mutableStateOf(false)  }

    val rating = movieViewModel.myRating.observeAsState().value
    val onWatchlist = movieViewModel.onWatchlist.observeAsState().value

    //Rating, Watchlist and addToList
    Spacer(modifier = Modifier.padding(8.dp))
    Row{
        //Rating and Watch button
        TextButton(onClick = { openRatingDialog.value = true }
        ) {
            if((rating ?: -1) > 0){
                Text(text = "$rating", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(30.dp)
                )
            }
            else if(rating == 0){
                Icon(imageVector = Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(30.dp))
            }else{
                Icon(imageVector = Icons.Outlined.Visibility, tint = Color.DarkGray, contentDescription = null, modifier = Modifier.size(30.dp))
            }
        }

        //Watchlist Button
        IconButton(onClick = { movieViewModel.changeOnWatchlist(id, !(onWatchlist ?: false)) }) {
            Icon(imageVector = if (onWatchlist == true) Icons.Default.WatchLater else Icons.Outlined.WatchLater,
                contentDescription = null,
                tint = if (onWatchlist == true) Color.White else Color.DarkGray,
                modifier = Modifier.size(30.dp)
            )
        }

        //Add to List Button
        IconButton(onClick = { openAddToListDialog.value = true }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                contentDescription = "Add to List",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        if(openRatingDialog.value){
            DiaryEditRatingDialog(
                rating = rating ?: 0,
                openRatingDialog = openRatingDialog.value,
                onAccept = { newRating->
                    openRatingDialog.value = false
                    movieViewModel.changeRating(id, newRating)
                }
            ) { openRatingDialog.value = false }
        }

        AddToListDialog(openAddToListDialog, movieDetails)
    }
}