package com.patch4code.loglinemovieapp.features.movie.presentation.components.dialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieMapper
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.AddListDialog
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.AddToListViewModel
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.AddToListViewModelFactory
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * AddToListDialog - Composable function that displays a dialog for adding a movie to a movie list.
 *
 * @author Patch4Code
 */
@Composable
fun AddToListDialog(
    openAddToListDialog: MutableState<Boolean>,
    movieDetails: MovieDetails?,
    db: LoglineDatabase,
    addToListViewModel: AddToListViewModel = viewModel(
        factory = AddToListViewModelFactory(db.movieListDao, db.movieInListDao)
    )
){
    // If the dialog is not open, return early
    if (!openAddToListDialog.value) return

    // Update user movie lists by calling the addToListViewModel when dialog is opened
    LaunchedEffect(Unit){
        addToListViewModel.loadUserMovieLists()
    }

    val context = LocalContext.current
    val toastText = stringResource(id = R.string.add_to_list_dialog_toast)

    val openAddListDialog = remember { mutableStateOf(false)  }

    // Create a Movie object from movie details
    val currentMovie = MovieMapper.mapToMovie(movieDetails)

    // Set the movie to be added when dialog is opened
    LaunchedEffect(Unit){
        addToListViewModel.setMovieToAdd(currentMovie)
    }

    // Get movie lists and track list
    val myUserMovieLists = addToListViewModel.userMovieLists.observeAsState().value
    val moviesInLists = addToListViewModel.moviesInLists.observeAsState().value
    val selectedList = remember { mutableStateOf<MovieList?>(null) }

    AlertDialog(
        modifier = Modifier.heightIn(min = 200.dp, max = 450.dp),
        onDismissRequest = { openAddToListDialog.value = false  },
        title = { Text(text = stringResource(id = R.string.add_to_list_dialog_title)) },
        text = {
            Column {
                FilledTonalButton(onClick = {openAddListDialog.value = true}) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = "Create new List")
                }
                AddToListDialogContent(addToListViewModel, myUserMovieLists, currentMovie, moviesInLists, selectedList)
            }
        },
        confirmButton = {
            Button(onClick = {
                openAddToListDialog.value = false
                addToListViewModel.addMovieToList(selectedList.value)
                Toast.makeText(context, "$toastText ${selectedList.value?.name}", Toast.LENGTH_SHORT).show()

            },
                enabled = selectedList.value != null
            ) {
                Text(text = stringResource(id = R.string.add_to_list_text))
            }
        },
        dismissButton = {
            Button(onClick = {
                openAddToListDialog.value = false
            }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        }
    )

    AddListDialog(
        openAddListDialog = openAddListDialog.value,
        onSave = { listName ->
            val currentTime = System.currentTimeMillis()
            addToListViewModel.createMovieList(
                MovieList(name = listName, timeCreated = currentTime, timeUpdated = currentTime)
            )
            openAddListDialog.value = false
        },
        onCancel = {openAddListDialog.value = false}
    )
}