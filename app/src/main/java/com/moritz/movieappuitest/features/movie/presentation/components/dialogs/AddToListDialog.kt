package com.moritz.movieappuitest.features.movie.presentation.components.dialogs

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.movie.domain.model.MovieDetails
import com.moritz.movieappuitest.features.movie.presentation.screen_movie.AddToListViewModel

@Composable
fun AddToListDialog(
    openAddToListDialog: MutableState<Boolean>,
    movieDetails: MovieDetails?,
    addToListViewModel: AddToListViewModel = viewModel()
){
    if (!openAddToListDialog.value) return

    val context = LocalContext.current

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
        title = { Text(text = "Add movie to list") },
        text = {
            AddToListDialogContent(myUserMovieLists, currentMovie, selectedList)
        },
        confirmButton = {
            Button(onClick = {
                openAddToListDialog.value = false
                addToListViewModel.addMovieToList(selectedList.value)
                Toast.makeText(context, "Movie added to List ${selectedList.value?.name}", Toast.LENGTH_SHORT).show()

            },
                enabled = selectedList.value != null
            ) {
                Text(text = "Add to List")
            }
        },
        dismissButton = {
            Button(onClick = {
                openAddToListDialog.value = false
            }) {
                Text(text = "Cancel")
            }
        }
    )
}