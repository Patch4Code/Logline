package com.patch4code.loglinemovieapp.features.core.presentation.components.movie_search_dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.GeneralMovieSearchViewModel

@Composable
fun MovieSearchDialog(
    title: String,
    confirmText: String,
    textInput: MutableState<String>,
    selectedMovie:  MutableState<Movie?>,
    searchResult: List<Movie>?,
    generalMovieSearchViewModel: GeneralMovieSearchViewModel,
    onDismiss:()->Unit,
    onConfirm:()->Unit
){

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = {
            val keyboardController = LocalSoftwareKeyboardController.current
            Column {
                MovieSearchDialogTextField(textInput, keyboardController, selectedMovie, generalMovieSearchViewModel)
                MovieSearchDialogLazyColumn(searchResult, selectedMovie)
            }
        },
        confirmButton = {
            Button(onClick = {onConfirm()}, enabled = selectedMovie.value != null
            ){
                Text(text = confirmText)
            }
        },
        dismissButton = { Button(onClick = { onDismiss() }) { Text(text = stringResource(id = R.string.cancel_button_text)) } }
    )
}