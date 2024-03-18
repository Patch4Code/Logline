package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.reviews.presentation.components.ReviewItem
import java.time.ZoneOffset

@Composable
fun ReviewsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    reviewsViewModel: ReviewsViewModel = viewModel()
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewsScreen)
    }

    val reviewedLogs = reviewsViewModel.reviewedLogs.observeAsState().value


    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(reviewedLogs?.sortedByDescending { loggedItem->
            loggedItem.date.toEpochSecond(ZoneOffset.UTC)
        } ?: emptyList())
        { loggedItem ->
            ReviewItem(loggedItem, navController)
        }
    }
}