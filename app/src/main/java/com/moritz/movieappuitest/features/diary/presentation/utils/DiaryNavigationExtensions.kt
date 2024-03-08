package com.moritz.movieappuitest.features.diary.presentation.utils

import android.util.Log
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.navigation.domain.model.Screen

object DiaryNavigationExtensions {

    fun NavController.navigateOnDiaryEditSaveOrDiscard(isDeleteAction: Boolean = false, comingFromDiaryView: Boolean? = null){

        val previousEntry = previousBackStackEntry
        //val secondToLastBackStackEntry =


        Log.e("DiaryNavigationExtensions","prepreviousEntry: $comingFromDiaryView")


        if(previousEntry?.destination?.route == Screen.DiaryScreen.route){
            //if previous screen was DiaryView navigate back to DiaryView and clear backstack
            navigate(Screen.DiaryScreen.route){
                popUpTo(Screen.DiaryScreen.route) {
                    inclusive = true
                }
            }
        } else{ //previous screen was ReviewDetailsView
            if (isDeleteAction){
                navigate(Screen.ReviewsScreen.route){
                    popUpTo(Screen.ReviewsScreen.route) {
                        inclusive = true
                    }
                }
            }else{
                popBackStack()
            }
        }
    }
}