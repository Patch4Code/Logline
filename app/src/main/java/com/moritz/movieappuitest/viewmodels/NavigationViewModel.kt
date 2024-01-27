package com.moritz.movieappuitest.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavigationViewModel: ViewModel() {
    val currentScreenTitle = mutableStateOf("")
}
fun updateScreenTitle(viewModel: NavigationViewModel, newTitle: String) {
    viewModel.currentScreenTitle.value = newTitle
    //Log.e("updateScreenTitle", viewModel.currentScreenTitle.value)
}