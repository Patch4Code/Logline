package com.moritz.movieappuitest.features.diary.presentation.screen_diary

import android.os.Build
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
import com.moritz.movieappuitest.features.diary.presentation.components.dialogs.DiaryEditReviewDialog
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditDateSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditDeleteSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditHeader
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditRatingSection
import com.moritz.movieappuitest.features.diary.presentation.components.editelement.DiaryEditReviewSection
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
    }

    val diaryEntry = diaryEditElementViewModel.diaryEntry.observeAsState().value

    var rating by remember { mutableStateOf(0)}
    var watchDate by remember { mutableStateOf("") }
    var review by remember { mutableStateOf("") }

    DisposableEffect(diaryEntry) {
        if (diaryEntry != null) {
            rating = diaryEntry.rating
            watchDate = diaryEntry.date
            review = diaryEntry.review
        }
        onDispose {}
    }

    val openDiscardDialog = remember { mutableStateOf(false)  }
    val openDatePickerDialog = remember { mutableStateOf(false)}
    val openRatingDialog = remember { mutableStateOf(false)}
    val openDeleteDialog = remember { mutableStateOf(false)}
    val openEditReviewDialog = remember { mutableStateOf(false)}

    BackHandler {
        openDiscardDialog.value = true
    }

    Column(modifier = Modifier.padding(16.dp)) {

        DiaryEditHeader(
            movieTitle = diaryEntry?.movie?.title ?: "N/A",
            moviePosterUrl = MovieHelper.processPosterUrl(diaryEntry?.movie?.posterUrl),
            movieYear = MovieHelper.extractYear(diaryEntry?.movie?.releaseDate)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        DiaryEditRatingSection(rating = rating, onButtonPressed = { openRatingDialog.value = true })
        DiaryEditDateSection(watchDate = watchDate, onButtonPressed = {openDatePickerDialog.value = true})
        DiaryEditReviewSection(reviewText = review, onEditReviewPressed = {openEditReviewDialog.value = true})
        DiaryEditDeleteSection(onButtonPressed = {openDeleteDialog.value = true})

        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)){
            DiaryEditSaveChangesSection(
                onSaveChanges = {
                    diaryEditElementViewModel.updatedDiaryEntry(rating, watchDate, review)
                    navController.navigateOnDiaryEditSaveOrDiscard(comingFromDiaryView = comingFromDiaryView)
                },
                onDiscardChanges = {openDiscardDialog.value = true}
            )
        }

        //Dialog Elements
        DiaryEditRatingDialog(
            rating = rating,
            openRatingDialog = openRatingDialog.value,
            onAccept = { newRating->
                openRatingDialog.value = false
                rating = newRating
            },
            onCancel = {openRatingDialog.value = false}
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

        DiaryEditReviewDialog(
            openEditReviewDialog = openEditReviewDialog.value,
            review = review,
            onSave = { editedReview->
                openEditReviewDialog.value = false
                review = editedReview
            },
            onCancel = { openEditReviewDialog.value = false }
        )

        DiaryEditDiscardDialog(
            openDiscardDialog = openDiscardDialog.value,
            onDiscard = { openDiscardDialog.value = false
                navController.navigateOnDiaryEditSaveOrDiscard(comingFromDiaryView = comingFromDiaryView)
            },
            onCancel = { openDiscardDialog.value = false }
        )

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