package com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * DiaryEditDiscardDialog - Composable function representing a dialog for discarding changes in the diary edit section.
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditDiscardDialog(openDiscardDialog: Boolean, onDiscard:() ->Unit, onCancel: () ->Unit){

    if (openDiscardDialog) {
        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = stringResource(id = R.string.diary_discard_changes_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.diary_discard_changes_dialog_text)) },
            confirmButton = {
                Button(onClick = { onDiscard() }) {
                    Text(text = stringResource(id = R.string.discard_button_text))
                }
            },
            dismissButton = {
                Button(onClick = { onCancel() }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            }
        )
    }
}