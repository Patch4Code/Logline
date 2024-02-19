package com.moritz.movieappuitest.views.diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.LoggedMovie
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.utils.MovieHelper
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import java.net.URLDecoder

@Composable
fun DiaryEditElementView(navController: NavController, navViewModel: NavigationViewModel, loggedElement: String?){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryEditElementScreen)
    }

    val decodedLoggedElementString = URLDecoder.decode(loggedElement, "UTF-8")
    val loggedElementData: LoggedMovie = JSONHelper.fromJson(decodedLoggedElementString)

    val movieTitle = loggedElementData.movie.title
    val movieYear = MovieHelper.extractYear(loggedElementData.movie.releaseDate)
    val moviePosterUrl = MovieHelper.processPosterUrl(loggedElementData.movie.posterUrl)

    Column(modifier = Modifier.padding(16.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)){
            AsyncImage(
                model = moviePosterUrl,
                contentDescription = "${movieTitle}-Poster"
            )
            Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
                Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
                Text(text = movieYear, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Edit Rating", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.padding(32.dp))

        Text(text = "Edit Date", style = MaterialTheme.typography.titleMedium)

        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)){
            Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ){
                IconButton(
                    onClick = { /*TODO*/
                        //Save Changes and navigate back to DiaryView
                    },

                    ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes")
                }
                IconButton(
                    onClick = { /*TODO*/
                        //Popup Window discard changes or cancel
                    },

                    ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Changes")
                }
            }
        }
    }

}