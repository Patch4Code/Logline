package com.patch4code.loglinemovieapp.features.search.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController

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