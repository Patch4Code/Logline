package com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * NavigationViewModel - ViewModel responsible for managing the current screen in the navigation.
 * Allows updating the current screen and overriding the current screen title for views with variable title.
 *
 * @author Patch4Code
 */
class NavigationViewModel: ViewModel() {

    private var _currentScreen = MutableLiveData<Screen>(Screen.HomeScreen)
    val currentScreen: LiveData<Screen>
        get() =_currentScreen

    // Updates the current screen with the provided new screen.
    fun updateScreen(newScreen: Screen) {
        _currentScreen.value = newScreen
    }

    // Overrides the title of the current screen with the provided title.
    fun overrideCurrentScreenTitle(title: UiText){
        _currentScreen.value!!.title = title
    }
}
