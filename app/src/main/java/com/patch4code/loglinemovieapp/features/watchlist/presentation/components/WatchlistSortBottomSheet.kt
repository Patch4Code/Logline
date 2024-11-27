package com.patch4code.loglinemovieapp.features.watchlist.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.patch4code.loglinemovieapp.features.watchlist.domain.model.WatchlistSortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistSortBottomSheet(showBottomSheet: MutableState<Boolean>, selectedSortOption: MutableState<WatchlistSortOption>){

    if(!showBottomSheet.value) return

    ModalBottomSheet(onDismissRequest = {showBottomSheet.value = false}){
        Text(text = "test", color = Color.White)
    }

}