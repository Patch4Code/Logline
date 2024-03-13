package com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.addElements.AddMovieToListConfirmButton
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.addElements.AddMovieToListLazyColumn
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.addElements.AddMovieToListTextField

@Composable
fun AddMovieToListDialog(openAddMovieDialog: Boolean, listViewModel: ListViewModel, closeDialog:()->Unit){

    if (!openAddMovieDialog) return

    val textInput = remember { mutableStateOf("") }
    val searchResult = listViewModel.searchedMovies.observeAsState().value
    val selectedMovie = remember { mutableStateOf<Movie?>(null) }

    AlertDialog(
        onDismissRequest = { closeDialog() },
        title = { Text(text = "Search Movie to add to list") },
        text = {
            val keyboardController = LocalSoftwareKeyboardController.current
            Column {
                AddMovieToListTextField(textInput, keyboardController, selectedMovie, listViewModel)
                AddMovieToListLazyColumn(searchResult, selectedMovie)
            }
        },
        confirmButton = { AddMovieToListConfirmButton(selectedMovie, listViewModel, onConfirmSuccessful = {closeDialog()}) },
        dismissButton = { Button(onClick = { closeDialog() }) { Text(text = "Cancel") } }
    )
}