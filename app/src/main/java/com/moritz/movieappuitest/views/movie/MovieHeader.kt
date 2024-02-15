package com.moritz.movieappuitest.views.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moritz.movieappuitest.utils.TmdbCredentials

@Composable
fun MovieHeader(title: String?, url: String?, releaseDate: String?, runtime: Int?){

    val movieTitle = title ?: "N/A"
    val moviePosterUrl: String = (url.takeIf { !it.isNullOrEmpty() }?.let { TmdbCredentials.POSTER_URL + it }
        ?: "")
    val movieYear: String = releaseDate.takeIf { !it.isNullOrEmpty() }?.split("-")?.get(0) ?: "N/A"
    val movieRuntime = runtime.toString()

    Row{
        //Poster
        Card (modifier = Modifier
            .height(200.dp)
            .width(133.dp),
            backgroundColor = Color.DarkGray
        )
        {
            if(moviePosterUrl.isNotEmpty()){
                AsyncImage(
                    model = moviePosterUrl,
                    contentDescription = "$movieTitle-Poster"
                )
            }
            else{
                Icon(imageVector = Icons.Default.ImageNotSupported, contentDescription ="$movieTitle-Poster")
            }
        }
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
            Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "$movieYear | $movieRuntime min", color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}