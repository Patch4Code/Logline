package com.patch4code.logline.features.core.presentation.components.movie_search_dialog

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.presentation.GeneralMovieSearchViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieSearchDialogTextField - Composable function for the text field in the movie search dialog
 *
 * @author Patch4Code
 */
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
        label = { Text(text = stringResource(id = R.string.search_text_field_label)) },
        singleLine = true,
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
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.search_icon_description))
            }
        }
    )
}