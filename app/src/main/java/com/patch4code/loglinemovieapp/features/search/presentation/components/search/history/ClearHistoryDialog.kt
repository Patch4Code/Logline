package com.patch4code.loglinemovieapp.features.search.presentation.components.search.history

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ClearHistoryDialog - Composable for displaying a dialog to clear search history.
 *
 * @author Patch4Code
 */
@Composable
fun ClearHistoryDialog(searchViewModel: SearchViewModel, openClearHistoryDialog: MutableState<Boolean>){

    if(!openClearHistoryDialog.value) return

    AlertDialog(
        onDismissRequest = { openClearHistoryDialog.value = false },
        title = { Text(stringResource(id = R.string.clear_history_title)) },
        text = { Text(text = stringResource(id = R.string.clear_history_text)) },
        confirmButton = {
            Button(onClick = {
                openClearHistoryDialog.value = false
                searchViewModel.clearSearchHistory()
            }) {
                Text(text = stringResource(id = R.string.clear_label))
            }
        },
        dismissButton = {
            Button(onClick = { openClearHistoryDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        }
    )
}