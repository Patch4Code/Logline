package com.moritz.movieappuitest.views.diary

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

    val context = LocalContext.current
    val openDiscardDialog = remember { mutableStateOf(false)  }

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryEditElementScreen)
    }

    val decodedLoggedElementString = URLDecoder.decode(loggedElement, "UTF-8")
    val loggedElementData: LoggedMovie = JSONHelper.fromJson(decodedLoggedElementString)

    val movieTitle = loggedElementData.movie.title
    val movieYear = MovieHelper.extractYear(loggedElementData.movie.releaseDate)
    val moviePosterUrl = MovieHelper.processPosterUrl(loggedElementData.movie.posterUrl)


    BackHandler {
        openDiscardDialog.value = true
    }


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

        if (openDiscardDialog.value) {
            AlertDialog(
                onDismissRequest = { openDiscardDialog.value = false },
                title = {Text(text = "Discard Changes?")},
                text = { Text(text = "") },
                confirmButton = {
                    Button(onClick = { navController.navigate(Screen.DiaryScreen.route) }) {
                        Text(text = "Discard")
                    }
                },
                dismissButton = {
                    Button(onClick = { openDiscardDialog.value = false }) {
                        Text(text = "Cancel")
                    }

                }
            )
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
                        //Update Data with Changes

                        //navigate back to DiaryView
                        navController.navigate(Screen.DiaryScreen.route)
                        Toast.makeText(context, "Diary Entry updated", Toast.LENGTH_LONG).show()

                    },

                    ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes")
                }
                IconButton(
                    onClick = {
                        //Popup Window discard changes or cancel
                        openDiscardDialog.value = true
                    },

                    ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Changes")
                }
            }
        }
    }

}