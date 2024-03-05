package com.moritz.movieappuitest.features.diary.presentation.screen_diary

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMoviesDummy
import com.moritz.movieappuitest.features.core.presentation.utils.JSONHelper
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditDatePickerDialog
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditDeleteDialog
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditDiscardDialog
import com.moritz.movieappuitest.features.core.presentation.components.DiaryEditRatingDialog
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditDateSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditDeleteSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditHeader
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditRatingSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditSaveChangesSection
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryEditElementView(navController: NavController, navViewModel: NavigationViewModel, loggedElement: String?){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryEditElementScreen)
    }
    val decodedLoggedElementString = URLDecoder.decode(loggedElement, "UTF-8")
    val loggedElementData: LoggedMovie = JSONHelper.fromJson(decodedLoggedElementString)

    val context = LocalContext.current

    val movieTitle = loggedElementData.movie.title
    var watchDate by remember { mutableStateOf(loggedElementData.date) }
    var rating by remember { mutableStateOf(loggedElementData.rating)}

    val openDiscardDialog = remember { mutableStateOf(false)  }
    val openDatePickerDialog = remember { mutableStateOf(false)}
    val openRatingDialog = remember { mutableStateOf(false)}
    val openDeleteDialog = remember { mutableStateOf(false)}

    BackHandler {
        openDiscardDialog.value = true
    }

    Column(modifier = Modifier.padding(16.dp)) {

        DiaryEditHeader(
            movieTitle = movieTitle,
            moviePosterUrl = MovieHelper.processPosterUrl(loggedElementData.movie.posterUrl),
            movieYear = MovieHelper.extractYear(loggedElementData.movie.releaseDate)
        )
        Spacer(modifier = Modifier.padding(16.dp))

        DiaryEditRatingSection(rating = rating, onButtonPressed = { openRatingDialog.value = true })
        Spacer(modifier = Modifier.padding(8.dp))

        DiaryEditDateSection(watchDate = watchDate, onButtonPressed = {openDatePickerDialog.value = true})
        Spacer(modifier = Modifier.padding(16.dp))

        DiaryEditDeleteSection(onButtonPressed = {openDeleteDialog.value = true})

        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)){
            DiaryEditSaveChangesSection(
                onSaveChanges = {
                    //Update Data with Changes

                    //Here only updates dummy temporary (later probably use an id for identification)
                    val updatedElement = LoggedMoviesDummy.find { it.movie.title == movieTitle }
                    updatedElement?.let {
                        it.date = watchDate
                        it.rating = rating
                    }
                    //----------------------------------

                    //navigate back to DiaryView and clear backstack
                    navController.navigate(Screen.DiaryScreen.route){
                        popUpTo(Screen.DiaryScreen.route) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(context, "Diary Entry updated", Toast.LENGTH_LONG).show()
                },
                onDiscardChanges = {openDiscardDialog.value = true}
            )
        }

        //Dialog Elements
        DiaryEditDiscardDialog(
            openDiscardDialog = openDiscardDialog.value,
            onDiscard = { openDiscardDialog.value = false
                navController.navigate(Screen.DiaryScreen.route){
                    popUpTo(Screen.DiaryScreen.route) {
                        inclusive = true
                    }
                }
            },
            onCancel = { openDiscardDialog.value = false }
        )

        DiaryEditDatePickerDialog(
            watchDate = watchDate,
            openDatePickerDialog = openDatePickerDialog.value,
            onAccept = { date->
                openDatePickerDialog.value = false
                watchDate = date
            },
            onCancel = { openDatePickerDialog.value = false }
        )

        DiaryEditRatingDialog(
            rating = rating,
            openRatingDialog = openRatingDialog.value,
            onAccept = { newRating->
                openRatingDialog.value = false
                rating = newRating
            }
        ) { openRatingDialog.value = false }

        DiaryEditDeleteDialog(
            openDeleteDialog = openDeleteDialog.value,
            onDelete = {
                openDeleteDialog.value = false

                //Delete Diary Element here
                //here just with dummy data with movie title as identifier
                LoggedMoviesDummy.removeIf { it.movie.title == movieTitle }

                navController.navigate(Screen.DiaryScreen.route){
                    popUpTo(Screen.DiaryScreen.route) {
                        inclusive = true
                    }
                }
            },
            onCancel = {openDeleteDialog.value = false}
        )
    }
}