package com.patch4code.loglinemovieapp.features.search.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * HandleFocusRequest - Composable function to handle focus requests for search input field.
 *
 * @author Patch4Code
 */
@Composable
fun HandleFocusRequest(
    searchFocusRequest: MutableState<Boolean>,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?
){
    LaunchedEffect(searchFocusRequest.value) {
        if (searchFocusRequest.value) {
            focusRequester.requestFocus()
            keyboardController?.show()
            searchFocusRequest.value = false
        }
    }
}