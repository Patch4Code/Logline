package com.patch4code.logline.features.movie.presentation.utils

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieTopBarNavigationHelper - Helper Object providing methods for handling Movie TopBar Navigation
 *
 * @author Patch4Code
 */
object MovieTopBarNavigationHelper {

    fun handleBackNavigation(navController: NavController, openPosterPopup: MutableState<Boolean>){
        if (openPosterPopup.value){
            openPosterPopup.value = false
        } else{
            navController.popBackStack()
        }
    }
}