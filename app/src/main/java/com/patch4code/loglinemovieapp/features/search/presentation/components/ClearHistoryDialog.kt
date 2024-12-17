package com.patch4code.loglinemovieapp.features.search.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel

@Composable
fun ClearHistoryDialog(searchViewModel: SearchViewModel, openClearHistoryDialog: MutableState<Boolean>){

    if(!openClearHistoryDialog.value) return

    AlertDialog(
        onDismissRequest = { openClearHistoryDialog.value = false },
        text = { Text(text = "Do you want to clear the Search History?") },
        confirmButton = {
            Button(onClick = {
                openClearHistoryDialog.value = false
                searchViewModel.clearSearchHistory()
            }) {
                Text(text = "Clear")
            }
        },
        dismissButton = {
            Button(onClick = { openClearHistoryDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        }
    )
}