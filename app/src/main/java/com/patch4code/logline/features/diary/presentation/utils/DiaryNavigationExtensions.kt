package com.patch4code.logline.features.diary.presentation.utils

import androidx.navigation.NavController

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryNavigationExtensions - Helper object for navigation related to the diary
 *
 * @author Patch4Code
 */

object DiaryNavigationExtensions {

    // Handles navigation after delete action in DiaryEditElementView
    fun NavController.navigateOnDiaryEntryDelete(comingFromDiaryView: Boolean?){
        if(comingFromDiaryView == true){
            popBackStack()
        }else{
            // not comingFromDiaryView means coming from ReviewDetailsView
            // go two iterations back to get back to ReviewsView
            popBackStack()
            popBackStack()
        }
    }
}