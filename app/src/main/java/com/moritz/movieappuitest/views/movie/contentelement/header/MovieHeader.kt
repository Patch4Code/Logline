package com.moritz.movieappuitest.views.movie.contentelement.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moritz.movieappuitest.utils.MovieHelper
import com.moritz.movieappuitest.viewmodels.MovieViewModel

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
    val moviePosterUrl: String = MovieHelper.processPosterUrl(url)
    val movieYear = MovieHelper.extractYear(releaseDate)
    val movieRuntime = runtime.toString()

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
            MovieHeaderToolbar(id, movieViewModel)
        }
    }
}