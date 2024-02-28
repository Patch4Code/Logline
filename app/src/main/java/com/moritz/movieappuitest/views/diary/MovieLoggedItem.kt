package com.moritz.movieappuitest.views.diary


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.LoggedMovie
import com.moritz.movieappuitest.utils.MovieHelper

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieLoggedItem(navController: NavController, loggedElement: LoggedMovie) {

    val movieId = loggedElement.movie.id.toString()
    val movieTitle = loggedElement.movie.title
    val movieYear = MovieHelper.extractYear(loggedElement.movie.releaseDate)
    val moviePosterUrl = MovieHelper.processPosterUrl(loggedElement.movie.posterUrl)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.MovieScreen.withArgs(movieId))
            }
    ){
        val parsedDate = MovieHelper.formatDate(dateString = loggedElement.date)
        Column (modifier = Modifier.padding(end = 16.dp)){
            parsedDate[1]?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
            parsedDate[0]?.let { Text(text = it, style = MaterialTheme.typography.headlineLarge) }
            parsedDate[2]?.let { Text(text = it, style = MaterialTheme.typography.titleSmall, color = Color.Gray) }
        }

        AsyncImage(
            model = moviePosterUrl,
            contentDescription = "${movieTitle}-Poster"
        )
        Column (modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .width(140.dp)){
            Text(text = loggedElement.movie.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movieYear, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.padding(4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "StarRate",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = "${loggedElement.rating}", color = Color.White, modifier = Modifier.align(
                    Alignment.CenterVertically
                ))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

    }
}