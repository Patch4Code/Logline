package com.moritz.movieappuitest.features.list.presentation.components.list.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditListDialog(
    initialMovieTitle: String,
    initialIsPublic: Boolean,
    openEditListDialog: Boolean,
    onSave:(listName: String, isPublic: Boolean)->Unit,
    onCancel:()->Unit
){

    if(openEditListDialog){

        val textInput = remember { mutableStateOf(initialMovieTitle) }
        val isPublic = remember { mutableStateOf(initialIsPublic) }

        AlertDialog(
            onDismissRequest = { onCancel() },//openDiscardDialog.value = false
            title = { Text(text = "Edit List") },
            text = {
                Column {
                    OutlinedTextField(
                        value = textInput.value,
                        onValueChange = { textInput.value = it },
                        label = { Text(text = "List name") })
                    Row (modifier = Modifier.padding(top = 8.dp)){
                        Switch(
                            checked = isPublic.value,
                            onCheckedChange = {
                                isPublic.value = it
                            }
                        )
                        Text(text = "Public", modifier = Modifier.padding(start = 8.dp).align(
                            Alignment.CenterVertically
                        ), )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { onSave(textInput.value, isPublic.value) }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = { onCancel() }) {//openDiscardDialog.value = false
                    Text(text = "Cancel")
                }
            }
        )
    }
}