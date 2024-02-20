package com.moritz.movieappuitest.views.diary

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.LoggedMovie
import com.moritz.movieappuitest.dataclasses.LoggedMoviesDummy
import com.moritz.movieappuitest.utils.DateHelper
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.utils.MovieHelper
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEditElementView(navController: NavController, navViewModel: NavigationViewModel, loggedElement: String?){


    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryEditElementScreen)
    }
    val decodedLoggedElementString = URLDecoder.decode(loggedElement, "UTF-8")
    val loggedElementData: LoggedMovie = JSONHelper.fromJson(decodedLoggedElementString)

    val context = LocalContext.current
    val openDiscardDialog = remember { mutableStateOf(false)  }

    var watchDate by remember { mutableStateOf(loggedElementData.date) }
    val openDatePickerDialog = remember { mutableStateOf(false)}

    var rating by remember { mutableStateOf(loggedElementData.rating)}
    val openRatingDialog = remember { mutableStateOf(false)}
    

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

        Spacer(modifier = Modifier.padding(16.dp))
        
        TextButton(onClick = { openRatingDialog.value = true }) {
            Row (modifier = Modifier.weight(1f)){
                Text(text = "Rating $rating" , style = MaterialTheme.typography.titleLarge)
                Icon(imageVector = Icons.Default.StarRate, contentDescription = null)
            }
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        TextButton(onClick = { openDatePickerDialog.value = true }) {
            Text(text = "Watched ${DateHelper.formatDateToDisplay(watchDate)}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        }


        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)){
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                thickness = 1.dp, color = Color.DarkGray
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ){
                IconButton(
                    onClick = { /*TODO*/
                        //Update Data with Changes

                        //Here only updates dummy temporary (later probably use an id for identification)
                        val updatedElement = LoggedMoviesDummy.find { it.movie.title == movieTitle }
                        updatedElement?.let {
                            it.date = watchDate
                        }
                        //----------------------------------

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

        if (openDiscardDialog.value) {
            AlertDialog(
                onDismissRequest = { openDiscardDialog.value = false },
                title = {Text(text = "Discard Changes?")},
                text = { Text(text = "When you continue, changes will be lost.") },
                confirmButton = {
                    Button(onClick = {
                        openDiscardDialog.value = false
                        navController.navigate(Screen.DiaryScreen.route) }) {
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

        if (openDatePickerDialog.value){
            val watchDateConverted = DateHelper.convertTimeStringToLong(watchDate)
            val datePickerState = rememberDatePickerState(watchDateConverted)
            val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

            DatePickerDialog(
                onDismissRequest = { openDatePickerDialog.value = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDatePickerDialog.value = false
                            var date = ""
                            if(datePickerState.selectedDateMillis != null){
                                date = DateHelper.convertLongToTimeSting(datePickerState.selectedDateMillis)
                            }
                            watchDate = date
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {openDatePickerDialog.value = false}) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if(openRatingDialog.value){
            AlertDialog(
                onDismissRequest = { openRatingDialog.value = false },
                title = {Text(text = "Change Rating")},
                text = {
                       Text(text = "Here comes element to change stars")
                },
                confirmButton = {
                    Button(onClick = {
                        openRatingDialog.value = false }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(onClick = { openRatingDialog.value = false }) {
                        Text(text = "Cancel")
                    }

                }
            )
        }
    }
}