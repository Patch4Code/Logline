package com.moritz.movieappuitest.features.diary.presentation.screen_diary

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.core.presentation.components.DiaryEditRatingDialog
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditDatePickerDialog
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditDeleteDialog
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditDiscardDialog
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditDateSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditDeleteSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditHeader
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditRatingSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditSaveChangesSection
import com.moritz.movieappuitest.features.diary.presentation.utils.DiaryNavigationExtensions.navigateOnDiaryEditSaveOrDiscard
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryEditElementView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    loggedElementId: String?,
    comingFromDiaryView: Boolean?,
    diaryEditElementViewModel: DiaryEditElementViewModel = viewModel()
    ){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryEditElementScreen)
        diaryEditElementViewModel.setDiaryEntryToEdit(loggedElementId)
        Log.e("DiaryEditElementViewModel", "launched")
    }

    val diaryEntry = diaryEditElementViewModel.diaryEntry.observeAsState().value

    var watchDate by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0)}

    DisposableEffect(diaryEntry) {
        if (diaryEntry != null) {
            watchDate = diaryEntry.date
            rating = diaryEntry.rating
        }
        onDispose {}
    }

    val openDiscardDialog = remember { mutableStateOf(false)  }
    val openDatePickerDialog = remember { mutableStateOf(false)}
    val openRatingDialog = remember { mutableStateOf(false)}
    val openDeleteDialog = remember { mutableStateOf(false)}

    BackHandler {
        openDiscardDialog.value = true
    }

    Column(modifier = Modifier.padding(16.dp)) {

        DiaryEditHeader(
            movieTitle = diaryEntry?.movie?.title ?: "N/A",
            moviePosterUrl = MovieHelper.processPosterUrl(diaryEntry?.movie?.posterUrl),
            movieYear = MovieHelper.extractYear(diaryEntry?.movie?.releaseDate)
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
                    diaryEditElementViewModel.updatedDiaryEntry(rating, watchDate)
                    navController.navigateOnDiaryEditSaveOrDiscard(comingFromDiaryView = comingFromDiaryView)
                },
                onDiscardChanges = {openDiscardDialog.value = true}
            )
        }

        //Dialog Elements
        DiaryEditDiscardDialog(
            openDiscardDialog = openDiscardDialog.value,
            onDiscard = { openDiscardDialog.value = false
                navController.navigateOnDiaryEditSaveOrDiscard(comingFromDiaryView = comingFromDiaryView)
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
            onDelete = { openDeleteDialog.value = false
                diaryEditElementViewModel.deleteDiaryEntry()
                navController.navigateOnDiaryEditSaveOrDiscard(isDeleteAction = true, comingFromDiaryView = comingFromDiaryView)
            },
            onCancel = {openDeleteDialog.value = false}
        )
    }
}