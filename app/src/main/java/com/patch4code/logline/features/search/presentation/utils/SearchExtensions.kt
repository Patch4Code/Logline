package com.patch4code.logline.features.search.presentation.utils

import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.patch4code.logline.features.search.presentation.screen_search.SearchViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchExtensions - Utility functions to enhance search-related operations.
 *
 * @author Patch4Code
 */
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