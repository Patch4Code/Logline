package com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryEditDeleteDialog - Composable function representing a delete confirmation dialog for the diary edit section.
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditDeleteDialog(openDeleteDialog: Boolean, onDelete:() ->Unit, onCancel: () ->Unit){

    if(openDeleteDialog){
        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = stringResource(id = R.string.diary_delete_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.diary_delete_dialog_text)) },
            confirmButton = {
                Button(onClick = { onDelete() }) {
                    Text(text = stringResource(id = R.string.delete_button_text))
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