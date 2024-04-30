package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.swipe.swipeToEditContainer
import com.patch4code.loglinemovieapp.features.diary.presentation.components.MovieLoggedItem
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryView - Composable function to display diary logs from the db
 * displays list of MovieLoggedItems and provides swipe to edit functionality
 *
 * @author Patch4Code
 */
@Composable
fun DiaryView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    diaryViewModel: DiaryViewModel = viewModel(
        factory = DiaryViewModelFactory(db.loggedMovieDao)
    )
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryScreen)
        diaryViewModel.getDiaryLogs()
    }

    val diaryLogs = diaryViewModel.diaryLogs.observeAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(diaryLogs ?: emptyList()) { loggedItem ->
            swipeToEditContainer(
                item = loggedItem,
                onEdit = {
                    // navigate to DiaryEditElementScreen on swipe (parameters: loggedElementId and comingFromDiaryView (boolean))
                    navController.navigate("${Screen.DiaryEditElementScreen.route}/${loggedItem.id}/${true}")
                })
            {_->
                MovieLoggedItem(navController = navController, loggedElement = loggedItem)
            }
        }
    }
}