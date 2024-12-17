package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DiscoverContent(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    selectedTabIndex: Int,
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