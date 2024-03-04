package com.moritz.movieappuitest.views.list.details.dialogs.addElements

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.viewmodels.ListViewModel

@Composable
fun AddMovieToListTextField(
    textInput: MutableState<String>,
    keyboardController:  SoftwareKeyboardController?,
    selectedMovie: MutableState<Movie?>,
    listViewModel: ListViewModel
){

    OutlinedTextField(
        value = textInput.value,
        onValueChange = {textInput.value = it},
        label = { Text(text = "Search Movie") },
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
}