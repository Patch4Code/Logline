package com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DeleteMovieFromListDialog - Composable function representing a dialog for deleting a movie from a list.
 *
 * @author Patch4Code
 */
@Composable
fun DeleteMovieFromListDialog(openDeleteMovieDialog: Boolean, onDelete:()->Unit, onCancel:()->Unit){

    if(openDeleteMovieDialog){
        AlertDialog(
            onDismissRequest = { onCancel() },//openDiscardDialog.value = false
            title = { Text(text = stringResource(id = R.string.list_delete_movie_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.list_delete_movie_dialog_text)) },
            confirmButton = {
                Button(onClick = { onDelete() }) {
                    Text(text = stringResource(id = R.string.list_delete_movie_dialog_confirm))
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