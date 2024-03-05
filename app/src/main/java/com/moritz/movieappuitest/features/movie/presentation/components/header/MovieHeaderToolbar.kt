package com.moritz.movieappuitest.features.movie.presentation.components.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.WatchLater
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
import com.moritz.movieappuitest.features.movie.presentation.screen_movie.MovieViewModel

@Composable
fun MovieHeaderToolbar(id: Int?, movieViewModel: MovieViewModel){

    val openRatingDialog = remember { mutableStateOf(false)  }
    val rating = movieViewModel.myRating.observeAsState().value
    val onWatchlist = movieViewModel.onWatchlist.observeAsState().value

    //Rating, Watchlist and addToList
    Spacer(modifier = Modifier.padding(8.dp))
    Row{
        TextButton(onClick = { openRatingDialog.value = true }) {
            val hasRating = (rating ?: 0) > 0
            if (hasRating) {
                Text(text = "$rating", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            }
            Icon(
                imageVector = if (hasRating) Icons.Default.StarRate else Icons.Default.StarOutline,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(onClick = { movieViewModel.changeOnWatchlist(id, !(onWatchlist ?: false)) }) {
            Icon(imageVector = if (onWatchlist == true) Icons.Default.WatchLater else Icons.Outlined.WatchLater,
                contentDescription = null,
                tint = if (onWatchlist == true) Color.White else Color.DarkGray,
                modifier = Modifier.size(30.dp)
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
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
    }
}