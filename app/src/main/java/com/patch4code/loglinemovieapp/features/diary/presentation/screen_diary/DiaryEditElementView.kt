package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.DiaryEditRatingDialog
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditDatePickerDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditDeleteDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditDiscardDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditReviewDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditDateSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditDeleteSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditHeader
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditRatingSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditReviewSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditSaveChangesSection
import com.patch4code.loglinemovieapp.features.diary.presentation.utils.DiaryNavigationExtensions.navigateOnDiaryEntryDelete
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarNoNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryEditElementView - Composable function representing the diary-edit-element screen view.
 * This composable function provides  UI for editing diary elements, including ratings, dates, reviews, and deletion
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditElementView(
    navController: NavController,
    loggedElementId: String?,
    comingFromDiaryView: Boolean?,
    db: LoglineDatabase,
    diaryEditElementViewModel: DiaryEditElementViewModel = viewModel(
        factory = DiaryEditElementViewModelFactory(db.loggedMovieDao)
    )
){

    LaunchedEffect(Unit) {
        diaryEditElementViewModel.setDiaryEntryToEdit(loggedElementId)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.DiaryEditElementScreen.title.asString())
    ProvideTopBarNoNavigationIcon()


    val scope = rememberCoroutineScope()

    val diaryEntry = diaryEditElementViewModel.diaryEntry.observeAsState().value

    var rating by remember { mutableStateOf(0)}
    var watchDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var review by remember { mutableStateOf("") }

    // Set initial values when diaryEntry is available
    DisposableEffect(diaryEntry) {
        if (diaryEntry != null) {
            rating = diaryEntry.rating
            watchDateTime = diaryEntry.date
            review = diaryEntry.review
        }
        onDispose {}
    }

    // Mutable state variables for controlling dialog visibility
    val openDiscardDialog = remember { mutableStateOf(false)  }
    val openDatePickerDialog = remember { mutableStateOf(false)}
    val openRatingDialog = remember { mutableStateOf(false)}
    val openDeleteDialog = remember { mutableStateOf(false)}
    val openEditReviewDialog = remember { mutableStateOf(false)}

    BackHandler {
        // Handle back press to open discard dialog
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
        DiaryEditDateSection(watchDateTime = watchDateTime, onButtonPressed = {openDatePickerDialog.value = true})
        DiaryEditReviewSection(reviewText = review, onEditReviewPressed = {openEditReviewDialog.value = true})
        DiaryEditDeleteSection(onButtonPressed = {openDeleteDialog.value = true})

        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)){
            DiaryEditSaveChangesSection(
                onSaveChanges = {
                    diaryEditElementViewModel.updatedDiaryEntry(rating, watchDateTime, review)
                    navController.popBackStack()
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
            watchDateTime = watchDateTime,
            openDatePickerDialog = openDatePickerDialog.value,
            onAccept = { date->
                openDatePickerDialog.value = false
                scope.launch {
                    watchDateTime = diaryEditElementViewModel.adjustedDateTime(date)
                }
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
                        navController.popBackStack()
            },
            onCancel = { openDiscardDialog.value = false }
        )
        DiaryEditDeleteDialog(
            openDeleteDialog = openDeleteDialog.value,
            onDelete = { openDeleteDialog.value = false
                diaryEditElementViewModel.deleteDiaryEntry()
                navController.navigateOnDiaryEntryDelete(comingFromDiaryView = comingFromDiaryView)
            },
            onCancel = {openDeleteDialog.value = false}
        )
    }
}