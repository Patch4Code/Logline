package com.patch4code.logline.features.profile.presentation.components.profile_edit.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.presentation.GeneralMovieSearchViewModel
import com.patch4code.logline.features.core.presentation.components.movie_search_dialog.MovieSearchDialog
import com.patch4code.logline.features.profile.presentation.screen_profile.ProfileViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SelectFavMovieDialog - Composable function that displays the dialog to select a favorite movie.
 * Uses the MovieSearchDialog.
 *
 * @author Patch4Code
 */
@Composable
fun SelectFavMovieDialog(
    openSelectFavMovieDialog: MutableState<Boolean>,
    favMovieClickedIndex: MutableState<Int>,
    profileViewModel: ProfileViewModel,
    generalMovieSearchViewModel: GeneralMovieSearchViewModel = viewModel()){

    if (!openSelectFavMovieDialog.value) return

    val textInput = remember { mutableStateOf("") }
    val searchResult = generalMovieSearchViewModel.searchedMovies.observeAsState().value
    val selectedMovie = remember { mutableStateOf<Movie?>(null) }

    MovieSearchDialog(
        title = stringResource(id = R.string.search_fav_movie_dialog_title),
        confirmText = stringResource(id = R.string.save_button_text),
        textInput = textInput,
        selectedMovie = selectedMovie,
        searchResult = searchResult,
        generalMovieSearchViewModel = generalMovieSearchViewModel,
        onDismiss = {openSelectFavMovieDialog.value = false},
        onConfirm = {
            openSelectFavMovieDialog.value = false
            profileViewModel.setFavMovieAtIndex(favMovieClickedIndex.value, selectedMovie.value!!)
        }
    )
}