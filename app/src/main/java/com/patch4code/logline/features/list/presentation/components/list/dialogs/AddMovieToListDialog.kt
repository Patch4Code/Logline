package com.patch4code.logline.features.list.presentation.components.list.dialogs

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.FilterOptions
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.domain.model.SortOption
import com.patch4code.logline.features.core.presentation.GeneralMovieSearchViewModel
import com.patch4code.logline.features.core.presentation.components.movie_search_dialog.MovieSearchDialog
import com.patch4code.logline.features.list.presentation.screen_list.ListViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * AddMovieToListDialog - Composable function representing a dialog for adding a movie to a list.
 * Uses the MovieSearchDialog composable.
 *
 *
 * @author Patch4Code
 */
@Composable
fun AddMovieToListDialog(
    openAddMovieDialog: MutableState<Boolean>,
    listViewModel: ListViewModel,
    sortOption: SortOption,
    filterOptions: FilterOptions,
    generalMovieSearchViewModel: GeneralMovieSearchViewModel = viewModel()
){

    if (!openAddMovieDialog.value) return

    val context = LocalContext.current
    val toastText = stringResource(id = R.string.toast_movie_already_on_list)

    val textInput = remember { mutableStateOf("") }
    val searchResult = generalMovieSearchViewModel.searchedMovies.observeAsState().value
    val selectedMovie = remember { mutableStateOf<Movie?>(null) }

    MovieSearchDialog(
        title = stringResource(id = R.string.list_add_movie_dialog_title),
        confirmText = stringResource(id = R.string.list_add_movie_dialog_confirm),
        textInput = textInput,
        selectedMovie = selectedMovie,
        searchResult = searchResult,
        generalMovieSearchViewModel = generalMovieSearchViewModel,
        onDismiss = {openAddMovieDialog.value = false},
        onConfirm = {
            selectedMovie.value?.let {movie->
                if (listViewModel.isMovieAlreadyOnList(movie)){
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                }else{
                    listViewModel.addMovieToList(movie, sortOption, filterOptions)
                    openAddMovieDialog.value = false
                }
            }
        }
    )
}