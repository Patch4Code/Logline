package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

@Composable
fun DiscoverContent(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    selectedTabIndex: Int,
    discoverViewModel: DiscoverViewModel = viewModel()
){

    if (selectedTabIndex != 1) return

    // searchFocusRequest disabled in this tab
    LaunchedEffect(searchFocusRequest.value) {
        searchFocusRequest.value = false
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Empty")

    }
}