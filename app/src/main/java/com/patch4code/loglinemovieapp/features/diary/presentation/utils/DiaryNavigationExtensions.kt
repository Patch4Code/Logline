package com.patch4code.loglinemovieapp.features.diary.presentation.utils

import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

object DiaryNavigationExtensions {

    fun NavController.navigateOnDiaryEditSaveOrDiscard(isDeleteAction: Boolean = false, comingFromDiaryView: Boolean?){

        if(comingFromDiaryView == true){
            handleDiaryScreenNavigation(isDeleteAction)
        }else{
           handleReviewScreenNavigation(isDeleteAction)
        }
    }


    //Navigation if edit request initially came from DiaryView
    private fun NavController.handleDiaryScreenNavigation(isDeleteAction: Boolean) {
        //if previous view was DiaryView or on delete navigate to DiaryView (and clear backstack)
        //just popBackStack brings problems with slide to edit functionality
        if(previousBackStackEntry?.destination?.route == Screen.DiaryScreen.route || isDeleteAction){
            navigate(Screen.DiaryScreen.route){
                popUpTo(Screen.DiaryScreen.route) {
                    inclusive = true
                }
            }
        }else{ //else just go back
            popBackStack()
        }
    }

    //Navigation if edit request came from ReviewDetailView
    private fun NavController.handleReviewScreenNavigation(isDeleteAction: Boolean) {
        //on delete go navigate to ReviewsView (and clear backstack)
        if(isDeleteAction){
            navigate(Screen.ReviewsScreen.route){
                popUpTo(Screen.ReviewsScreen.route) {
                    inclusive = true
                }
            }
        }else{ //else just go back
            popBackStack()
        }
    }

}