package com.moritz.movieappuitest.views.list

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteListDialog(openDeleteListDialog: Boolean, onDelete:()->Unit, onCancel:()->Unit){

    if(openDeleteListDialog){

        AlertDialog(
            onDismissRequest = { onCancel() },//openDiscardDialog.value = false
            title = { Text(text = "Delete List") },
            text = { Text(text = "Are you sure you want to delete the list?")},
            confirmButton = {
                Button(onClick = { onDelete() }) {
                    Text(text = "Delete List")
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