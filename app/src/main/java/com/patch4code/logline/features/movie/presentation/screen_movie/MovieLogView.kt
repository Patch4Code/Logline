package com.patch4code.logline.features.movie.presentation.screen_movie

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.presentation.components.DiaryEditRatingDialog
import com.patch4code.logline.features.core.presentation.utils.JSONHelper
import com.patch4code.logline.features.core.presentation.utils.MovieHelper
import com.patch4code.logline.features.diary.presentation.components.dialogs.DiaryEditDatePickerDialog
import com.patch4code.logline.features.diary.presentation.components.dialogs.DiaryEditDiscardDialog
import com.patch4code.logline.features.diary.presentation.components.dialogs.DiaryEditReviewDialog
import com.patch4code.logline.features.diary.presentation.components.editelement.DiaryEditDateSection
import com.patch4code.logline.features.diary.presentation.components.editelement.DiaryEditHeader
import com.patch4code.logline.features.diary.presentation.components.editelement.DiaryEditRatingSection
import com.patch4code.logline.features.diary.presentation.components.editelement.DiaryEditReviewSection
import com.patch4code.logline.features.diary.presentation.components.editelement.DiaryEditSaveChangesSection
import com.patch4code.logline.features.navigation.domain.model.Screen
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarNoNavigationIcon
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.logline.room_database.LoglineDatabase
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieLogView - Composable function representing the movie-log screen view.
 * Displays the UI for logging a movie.
 *
 * @author Patch4Code
 */
@SuppressLint("SimpleDateFormat")
@Composable
fun MovieLogView(
    navController: NavController,
    db: LoglineDatabase,
    movieString: String?,
    movieLogViewModel: MovieLogViewModel = viewModel(
        factory = MovieLogViewModelFactory(db.loggedMovieDao ,db.movieUserDataDao)
    )
){

    // TopBar config
    ProvideTopBarTitle(title = Screen.MovieLogScreen.title.asString())
    ProvideTopBarNoNavigationIcon()


    val decodedMovieString = URLDecoder.decode(movieString, "UTF-8")
    val movie: Movie = JSONHelper.fromJson(decodedMovieString)

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val toastText = stringResource(id = R.string.movie_log_toast)

    var rating by remember { mutableIntStateOf(0) }
    var watchDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var review by remember { mutableStateOf("") }

    val openDiscardDialog = remember { mutableStateOf(false)  }
    val openDatePickerDialog = remember { mutableStateOf(false) }
    val openRatingDialog = remember { mutableStateOf(false) }
    val openEditReviewDialog = remember { mutableStateOf(false) }

    BackHandler {
        openDiscardDialog.value = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                DiaryEditHeader(
                    movieTitle = movie.title,
                    moviePosterUrl = MovieHelper.processPosterUrl(movie.posterUrl),
                    movieYear = MovieHelper.extractYear(movie.releaseDate)
                )

                DiaryEditRatingSection(rating = rating, onButtonPressed = { openRatingDialog.value = true })
                DiaryEditDateSection(watchDateTime = watchDateTime, onButtonPressed = { openDatePickerDialog.value = true })
                DiaryEditReviewSection(reviewText = review, onEditReviewPressed = { openEditReviewDialog.value = true })
            }
        }
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(16.dp)) {
            DiaryEditSaveChangesSection(
                isEdit = false,
                onSaveChanges = {
                    movieLogViewModel.addMovieLog(movie, watchDateTime, rating, review)
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                },
                onDiscardChanges = { openDiscardDialog.value = true }
            )
        }
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
        watchDateTime = watchDateTime,
        openDatePickerDialog = openDatePickerDialog.value,
        onAccept = { date->
            openDatePickerDialog.value = false
            scope.launch {
                watchDateTime =  movieLogViewModel.adjustedDateTime(date)
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
}