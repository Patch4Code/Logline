package com.moritz.movieappuitest.views.movie.contentelement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.compose.AsyncImage
import com.moritz.movieappuitest.utils.TmdbCredentials
import com.moritz.movieappuitest.viewmodels.MovieViewModel
import com.moritz.movieappuitest.views.diary.editelement.dialogs.DiaryEditRatingDialog

@Composable
fun MovieHeader(
    id: Int?,
    title: String?,
    url: String?,
    releaseDate: String?,
    runtime: Int?,
    movieViewModel: MovieViewModel
){

    val movieTitle = title ?: "N/A"
    val moviePosterUrl: String = (url.takeIf { !it.isNullOrEmpty() }?.let { TmdbCredentials.POSTER_URL + it }
        ?: "")
    val movieYear: String = releaseDate.takeIf { !it.isNullOrEmpty() }?.split("-")?.get(0) ?: "N/A"
    val movieRuntime = runtime.toString()

    val openRatingDialog = remember { mutableStateOf(false)  }
    val rating = movieViewModel.myRating.observeAsState().value
    val onWatchlist = movieViewModel.onWatchlist.observeAsState().value

    Row{
        //Poster
        Card (modifier = Modifier
            .height(200.dp)
            .width(133.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        )
        {
            if(moviePosterUrl.isNotEmpty()){
                AsyncImage(
                    model = moviePosterUrl,
                    contentDescription = "$movieTitle-Poster"
                )
            }
            else{
                Icon(imageVector = Icons.Default.ImageNotSupported, contentDescription ="$movieTitle-Poster", modifier = Modifier.fillMaxSize())
            }
        }
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
            Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "$movieYear | $movieRuntime min", color = Color.White, style = MaterialTheme.typography.bodyMedium)

            //Rating and Watchlist
            Spacer(modifier = Modifier.padding(8.dp))
            Row{
                TextButton(onClick = { openRatingDialog.value = true }) {
                    if((rating ?: 0) > 0){
                        Text(text = "$rating", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                        Icon(imageVector = Icons.Default.StarRate, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(30.dp))
                    }
                    else{
                        Icon(imageVector = Icons.Default.StarOutline, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(30.dp))
                    }
                }
                IconButton(onClick = { movieViewModel.changeOnWatchlist(id, !(onWatchlist ?: false)) }) {
                    if(onWatchlist == true){
                        Icon(imageVector = Icons.Default.BookmarkAdded,
                            contentDescription = "Movie is on Watchlist",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    else{
                        Icon(imageVector = Icons.Default.BookmarkAdd,
                            contentDescription = "Movie is not on Watchlist",
                            tint = Color.Gray,
                            modifier = Modifier.size(30.dp))
                    }

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
    }
}