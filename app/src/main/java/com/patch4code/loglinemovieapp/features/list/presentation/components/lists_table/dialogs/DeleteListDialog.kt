package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DeleteListDialog - Composable function displaying a dialog for deleting a list.
 *
 * @author Patch4Code
 */
@Composable
fun DeleteListDialog(openDeleteListDialog: Boolean, onDelete:()->Unit, onCancel:()->Unit){

    if(openDeleteListDialog){
        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = stringResource(id = R.string.delete_list_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.delete_list_dialog_text))},
            confirmButton = {
                Button(onClick = { onDelete() }) {
                    Text(text = stringResource(id = R.string.delete_list_text))
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