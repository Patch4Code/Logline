package com.moritz.movieappuitest.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.LoggedMoviesDummy
import com.moritz.movieappuitest.utils.JSONHelper.toJson
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.moviecards.MovieLoggedItem
import com.moritz.movieappuitest.views.moviecards.swipeToEditContainer
import java.net.URLEncoder
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.DiaryScreen)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        items(LoggedMoviesDummy.sortedByDescending {loggedItem->
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(loggedItem.date, formatter)
            localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

        }) { loggedItem ->
            val jsonLoggedItem = loggedItem.toJson()
            val encodedJsonLoggedItem = URLEncoder.encode(jsonLoggedItem, "UTF-8")
            swipeToEditContainer(
                item = loggedItem,
                onEdit = {
                    navController.navigate(Screen.DiaryEditElementScreen.withArgs(encodedJsonLoggedItem))
                })
            {_->
                MovieLoggedItem(navController = navController, loggedElement = loggedItem)
            }
        }
    }
}