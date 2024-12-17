package com.patch4code.loglinemovieapp.features.search.presentation.utils

import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel

object SearchExtensions {

    fun triggerSearch(
        query: String,
        keyboardController: SoftwareKeyboardController?,
        focusManager: FocusManager,
        searchViewModel: SearchViewModel
    ){
        if(query.isNotBlank()){
            keyboardController?.hide()
            focusManager.clearFocus()
            searchViewModel.searchMovie(query)
        }
    }
}