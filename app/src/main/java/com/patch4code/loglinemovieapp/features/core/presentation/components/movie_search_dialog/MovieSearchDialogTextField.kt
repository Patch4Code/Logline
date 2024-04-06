package com.patch4code.loglinemovieapp.features.core.presentation.components.movie_search_dialog

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
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.GeneralMovieSearchViewModel

@Composable
fun MovieSearchDialogTextField(
    textInput: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    selectedMovie: MutableState<Movie?>,
    generalMovieSearchViewModel: GeneralMovieSearchViewModel
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

                    generalMovieSearchViewModel.searchMovie(textInput.value)
                }
            }
        ),
        trailingIcon = {
            IconButton(onClick = {
                if(textInput.value.isNotBlank()){
                    keyboardController?.hide()
                    selectedMovie.value = null

                    generalMovieSearchViewModel.searchMovie(textInput.value)
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
}