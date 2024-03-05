package com.moritz.movieappuitest.features.movie.presentation.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.movie.domain.model.MovieDetails
import com.moritz.movieappuitest.features.movie.presentation.screen_movie.AddToListViewModel

@Composable
fun AddToListDialog(
    openAddToListDialog: MutableState<Boolean>,
    movieDetails: MovieDetails?,
    addToListViewModel: AddToListViewModel = viewModel()
){

    if (!openAddToListDialog.value) return

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


    AlertDialog(
        onDismissRequest = { openAddToListDialog.value = false  },
        title = { Text(text = "Add movie to lists") },
        text = {

            LazyColumn(modifier = Modifier
                .padding(8.dp)) {
                myUserMovieLists?.forEach { list ->
                    item {
                        Row {
                            Column (modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)){
                                Text(text = list.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
                            }
                            Column(modifier = Modifier.padding(8.dp)){
                                Icon(
                                    imageVector = if(list.isPublic) Icons.Default.Public else Icons.Default.Lock,
                                    contentDescription = if(list.isPublic) "List is public" else "List is private",
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                openAddToListDialog.value = false
            }) {
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