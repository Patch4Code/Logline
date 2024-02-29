package com.moritz.movieappuitest.views.list.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.viewmodels.ListViewModel

@Composable
fun AddMovieToListDialog(openAddMovieDialog: Boolean, listViewModel: ListViewModel, closeDialog:()->Unit){

    if(openAddMovieDialog){

        val textInput = remember { mutableStateOf("") }

        val searchResult = listViewModel.searchedMovies.observeAsState().value

        val selectedMovie = remember { mutableStateOf<Movie?>(null) }


        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = { Text(text = "Search Movie to add to list") },
            text = {
                val keyboardController = LocalSoftwareKeyboardController.current
                Column {
                    OutlinedTextField(
                        value = textInput.value,
                        onValueChange = {textInput.value = it},
                        label = { Text(text = "Search Movie")},
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if(textInput.value.isNotBlank()){
                                    keyboardController?.hide()
                                    selectedMovie.value = null

                                    listViewModel.searchMovie(textInput.value)
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                if(textInput.value.isNotBlank()){
                                    keyboardController?.hide()
                                    selectedMovie.value = null

                                    listViewModel.searchMovie(textInput.value)
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                            }
                        }
                    )
                    LazyColumn (modifier = Modifier.padding(top = 8.dp)) {
                        searchResult?.forEach{ movie->
                            item{
                                Card(colors = CardDefaults.cardColors(containerColor = if(selectedMovie.value == movie) Color.Gray else Color.Transparent)) {
                                    MovieListAddMovieCard(
                                        movie = movie,
                                        selectMovie = {clickedMovie->
                                            selectedMovie.value = clickedMovie
                                        }
                                    )
                                }

                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedMovie.value?.let { listViewModel.addMovieToList(it) }
                        closeDialog()
                    },
                    enabled = selectedMovie.value != null
                ) {
                    Text(text = "Add Movie")
                }
            },
            dismissButton = {
                Button(onClick = { closeDialog() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}