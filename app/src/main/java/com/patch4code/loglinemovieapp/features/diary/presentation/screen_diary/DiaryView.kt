package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog.SortFilterDialog
import com.patch4code.loglinemovieapp.features.core.presentation.components.swipe.SwipeToEditContainer
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.FilterOptionsSaver
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.SortOptionSaver
import com.patch4code.loglinemovieapp.features.diary.domain.model.DiaryAndReviewSortOptions
import com.patch4code.loglinemovieapp.features.diary.presentation.components.EmptyDiaryText
import com.patch4code.loglinemovieapp.features.diary.presentation.components.MovieLoggedItem
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarSortFilterActions
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryView - Composable function representing the diary screen view.
 * Displays list of MovieLoggedItems and provides swipe to edit functionality.
 *
 * @author Patch4Code
 */
@Composable
fun DiaryView(
    navController: NavController,
    db: LoglineDatabase,
    diaryViewModel: DiaryViewModel = viewModel(
        factory = DiaryViewModelFactory(db.loggedMovieDao)
    )
){

    val selectedSortOption: MutableState<SortOption> =
        rememberSaveable(stateSaver = SortOptionSaver.saver) { mutableStateOf(SortOption.ByAddedDesc) }
    val selectedFilterOptions =
        rememberSaveable(stateSaver = FilterOptionsSaver.saver) { mutableStateOf(FilterOptions()) }

    val showFilterDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        diaryViewModel.loadDiaryLogs(selectedSortOption.value, selectedFilterOptions.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.DiaryScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortFilterActions(sortFilterOnClickAction = { showFilterDialog.value = true})


    val diaryLogs = diaryViewModel.diaryLogs.observeAsState().value

    if(diaryLogs.isNullOrEmpty()){
        EmptyDiaryText(FilterHelper.isAnyFilterApplied(selectedFilterOptions.value))
    }else{
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(diaryLogs) { loggedItem ->

                SwipeToEditContainer(
                    item = loggedItem,
                    onEdit = {
                        // navigate to DiaryEditElementScreen on swipe (parameters: loggedElementId and comingFromDiaryView (boolean))
                        navController.navigate("${Screen.DiaryEditElementScreen.route}/${loggedItem.id}/${true}")
                    }
                ){
                    MovieLoggedItem(navController, loggedItem)
                }
            }
        }
    }
    SortFilterDialog(showFilterDialog, DiaryAndReviewSortOptions.options, selectedSortOption, selectedFilterOptions){
        diaryViewModel.loadDiaryLogs(selectedSortOption.value, selectedFilterOptions.value)
    }
}