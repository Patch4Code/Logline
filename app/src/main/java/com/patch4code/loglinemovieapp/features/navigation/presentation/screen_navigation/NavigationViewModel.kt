package com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

class NavigationViewModel: ViewModel() {

    private var _currentScreen = MutableLiveData<Screen>(Screen.HomeScreen)
    val currentScreen: LiveData<Screen>
        get() =_currentScreen

    fun updateScreen(newScreen: Screen) {
        _currentScreen.value = newScreen
    }

    fun overrideCurrentScreenTitle(title: UiText){
        _currentScreen.value!!.title = title
    }
}
