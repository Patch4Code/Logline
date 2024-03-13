package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.components.DiaryEditRatingDialog
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditDatePickerDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditDiscardDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs.DiaryEditReviewDialog
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditDateSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditHeader
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditRatingSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditReviewSection
import com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement.DiaryEditSaveChangesSection
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun MovieLogView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    movieString: String?,
    movieLogViewModel: MovieLogViewModel = viewModel(),
    ){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MovieLogScreen)
    }

    val decodedMovieString = URLDecoder.decode(movieString, "UTF-8")
    val movie: Movie = JSONHelper.fromJson(decodedMovieString)


    val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
    val context = LocalContext.current

    var rating by remember { mutableStateOf(0) }
    var watchDate by remember { mutableStateOf(currentDate) }
    var review by remember { mutableStateOf("") }

    val openDiscardDialog = remember { mutableStateOf(false)  }
    val openDatePickerDialog = remember { mutableStateOf(false) }
    val openRatingDialog = remember { mutableStateOf(false) }
    val openEditReviewDialog = remember { mutableStateOf(false) }

    BackHandler {
        openDiscardDialog.value = true
    }

    Column(modifier = Modifier.padding(16.dp)) {

        DiaryEditHeader(
            movieTitle = movie.title,
            moviePosterUrl = MovieHelper.processPosterUrl(movie.posterUrl),
            movieYear = MovieHelper.extractYear(movie.releaseDate)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        DiaryEditRatingSection(rating = rating, onButtonPressed = { openRatingDialog.value = true })
        DiaryEditDateSection(watchDate = watchDate, onButtonPressed = {openDatePickerDialog.value = true})
        DiaryEditReviewSection(reviewText = review, onEditReviewPressed = {openEditReviewDialog.value = true})

        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)){
            DiaryEditSaveChangesSection(
                isEdit = false,
                onSaveChanges = {
                    movieLogViewModel.addMovieLog(movie, watchDate, rating, review)
                    Toast.makeText(context, "Diary Entry Added", Toast.LENGTH_LONG).show()
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
            onCancel = {openRatingDialog.value = false},
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
                navController.popBackStack()
            },
            onCancel = { openDiscardDialog.value = false }
        )
    }
}