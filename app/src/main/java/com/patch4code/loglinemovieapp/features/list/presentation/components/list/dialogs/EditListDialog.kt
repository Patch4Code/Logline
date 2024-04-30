package com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * EditListDialog - Composable function representing a dialog for editing a list.
 * Allows the user to change the list name.
 *
 * @author Patch4Code
 */
@Composable
fun EditListDialog(
    initialMovieTitle: String,
    initialIsRankedState: Boolean, //not used yet
    openEditListDialog: Boolean,
    onSave:(listName: String, isPublic: Boolean)->Unit,
    onCancel:()->Unit
){

    if(openEditListDialog){

        val textInput = remember { mutableStateOf(initialMovieTitle) }
        val isRanked = remember { mutableStateOf(initialIsRankedState) }

        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = stringResource(id = R.string.edit_list_text)) },
            text = {
                Column {
                    OutlinedTextField(
                        value = textInput.value,
                        onValueChange = { textInput.value = it },
                        label = { Text(text = stringResource(id = R.string.list_dialog_text_field_label)) })

                    // ranked list not implemented yet
                    /*
                    Row (modifier = Modifier.padding(top = 8.dp)){
                        Switch(
                            checked = isRanked.value,
                            onCheckedChange = {
                                isRanked.value = it
                            }
                        )
                        Text(text = "Public", modifier = Modifier.padding(start = 8.dp).align(
                            Alignment.CenterVertically
                        ), )
                    }*/
                }
            },
            confirmButton = {
                Button(onClick = { onSave(textInput.value, isRanked.value) }) {
                    Text(text = stringResource(id = R.string.save_button_text))
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