package com.moritz.movieappuitest.features.diary.presentation.utils

import androidx.navigation.NavController
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.navigation.domain.model.Screen

fun navigateOnDiaryEditSaveChanges(navController: NavController, diaryEntry: LoggedMovie){

    val previousEntry = navController.previousBackStackEntry

    if(previousEntry?.destination?.route == Screen.DiaryScreen.route){
        //if previous screen was DiaryView navigate back to DiaryView and clear backstack
        navController.navigate(Screen.DiaryScreen.route){
            popUpTo(Screen.DiaryScreen.route) {
                inclusive = true
            }
        }
    }

    else{ //previous screen was ReviewDetailsView
        //val jsonLoggedItem = diaryEntry?.toJson()
        //val encodedJsonLoggedItem = URLEncoder.encode(jsonLoggedItem, "UTF-8")
        val diaryEntryId = diaryEntry.id

        navController.navigate(Screen.ReviewDetailScreen.withArgs(diaryEntryId)){
            popUpTo(Screen.ReviewsScreen.route) {
                inclusive = false
            }
        }



        //navController.popBackStack()
    }


}