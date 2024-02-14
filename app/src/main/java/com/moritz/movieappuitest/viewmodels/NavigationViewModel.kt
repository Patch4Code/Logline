package com.moritz.movieappuitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.Screen

class NavigationViewModel: ViewModel() {

    private var _currentScreen = MutableLiveData<Screen>(Screen.HomeScreen)
    val currentScreen: LiveData<Screen>
        get() =_currentScreen

    fun updateScreen(newScreen: Screen) {
        _currentScreen.value = newScreen
    }
}
