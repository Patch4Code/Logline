package com.patch4code.loglinemovieapp.features.search.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel

@Composable
fun HandleClearSearch(textInput: MutableState<TextFieldValue>, searchViewModel: SearchViewModel) {
    LaunchedEffect(textInput.value.text) {
        if(textInput.value.text.isEmpty()){
            searchViewModel.clearSearchedMovies()
        }
    }
}