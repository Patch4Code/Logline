package com.patch4code.logline.features.search.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.patch4code.logline.features.search.presentation.screen_search.SearchViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * HandleClearSearch - Composable function that observes changes in the text input
 * and clears the search results when the input is empty.
 *
 * @author Patch4Code
 */
@Composable
fun HandleClearSearch(textInput: MutableState<TextFieldValue>, searchViewModel: SearchViewModel) {
    LaunchedEffect(textInput.value.text) {
        if(textInput.value.text.isEmpty()){
            searchViewModel.clearSearchedMovies()
        }
    }
}