package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

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
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.FilterOptionsSaver
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.SortOptionSaver
import com.patch4code.loglinemovieapp.features.diary.domain.model.DiaryAndReviewSortOptions
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarSortFilterActions
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.EmptyReviewsText
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewItem
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewsView - Composable function representing the review screen view.
 * Displays a list of the users reviews (logs that contain a review).
 * Navigates the user to the ReviewDetailsView on click on ReviewItem.
 *
 * @author Patch4Code
 */
@Composable
fun ReviewsView(
    navController: NavController,
    db: LoglineDatabase,
    reviewsViewModel: ReviewsViewModel = viewModel(
        factory = ReviewsViewModelFactory(db.loggedMovieDao)
    )
){

    val selectedSortOption: MutableState<SortOption> =
        rememberSaveable(stateSaver = SortOptionSaver.saver) { mutableStateOf(SortOption.ByAddedDesc) }
    val selectedFilterOptions =
        rememberSaveable(stateSaver = FilterOptionsSaver.saver) { mutableStateOf(FilterOptions()) }

    val showFilterDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        reviewsViewModel.getReviewedLogs(selectedSortOption.value, selectedFilterOptions.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.ReviewsScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortFilterActions(sortFilterOnClickAction = { showFilterDialog.value = true })


    val reviewedLogs = reviewsViewModel.reviewedLogs.observeAsState().value

    if(reviewedLogs.isNullOrEmpty()){
        EmptyReviewsText(FilterHelper.isAnyFilterApplied(selectedFilterOptions.value))
    }else{
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(reviewedLogs)
            { loggedItem ->
                ReviewItem(loggedItem, navController)
            }
        }
    }
    SortFilterDialog(showFilterDialog, DiaryAndReviewSortOptions.options, selectedSortOption, selectedFilterOptions){
        reviewsViewModel.getReviewedLogs(selectedSortOption.value, selectedFilterOptions.value)
    }
}