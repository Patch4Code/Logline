package com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * NavigationViewModel - ViewModel responsible for managing the current screen in the navigation.
 * Allows updating the current screen and overriding the current screen title for views with variable title.
 *
 * @author Patch4Code
 */
class TopBarViewModel: ViewModel() {

    var title by mutableStateOf<@Composable () -> Unit>({ }, referentialEqualityPolicy())

    val defaultNavigationIcon: @Composable () -> Unit = {}
    var navigationIcon by mutableStateOf<@Composable () -> Unit>(defaultNavigationIcon)

    var actions by mutableStateOf<@Composable RowScope.() -> Unit>({ }, referentialEqualityPolicy())
}
