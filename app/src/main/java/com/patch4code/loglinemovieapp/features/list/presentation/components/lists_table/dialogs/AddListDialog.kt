package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun AddListDialog(openAddListDialog: Boolean, onSave:(listName: String, isPublic: Boolean)->Unit, onCancel:()->Unit){

    if(openAddListDialog){

        val textInput = remember { mutableStateOf("") }
        val isRanked = remember { mutableStateOf(false) } //not used yet

        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = "New List") },
            text = {
                Column {
                    OutlinedTextField(
                        value = textInput.value,
                        onValueChange = { textInput.value = it },
                        label = { Text(text = "List name") })

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
                    }
                    */
                }
            },
            confirmButton = {
                Button(onClick = { onSave(textInput.value, isRanked.value) }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = { onCancel() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}