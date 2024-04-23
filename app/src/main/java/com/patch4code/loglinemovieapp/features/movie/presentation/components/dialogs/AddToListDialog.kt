package com.patch4code.loglinemovieapp.features.movie.presentation.components.dialogs

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.AddToListViewModel
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.AddToListViewModelFactory
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun AddToListDialog(
    openAddToListDialog: MutableState<Boolean>,
    movieDetails: MovieDetails?,
    db: LoglineDatabase,
    addToListViewModel: AddToListViewModel = viewModel(
        factory = AddToListViewModelFactory(db.movieListDao)
    )
){
    if (!openAddToListDialog.value) return

    LaunchedEffect(Unit){
        addToListViewModel.updateUserMovieLists()
    }

    val context = LocalContext.current
    val toastText = stringResource(id = R.string.add_to_list_dialog_toast)

    val currentMovie = Movie(
        title = movieDetails?.title ?: "N/A",
        id = movieDetails?.id ?: -1,
        releaseDate = movieDetails?.releaseDate ?: "N/A",
        posterUrl = movieDetails?.posterPath ?: ""
    )

    LaunchedEffect(Unit){
        addToListViewModel.setMovieToAdd(currentMovie)
    }
    val myUserMovieLists = addToListViewModel.userMovieLists.observeAsState().value
    val selectedList = remember { mutableStateOf<MovieList?>(null) }

    AlertDialog(
        onDismissRequest = { openAddToListDialog.value = false  },
        title = { Text(text = stringResource(id = R.string.add_to_list_dialog_title)) },
        text = {
            AddToListDialogContent(myUserMovieLists, currentMovie, selectedList)
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
}