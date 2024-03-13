package com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DiaryEditDeleteDialog(openDeleteDialog: Boolean, onDelete:() ->Unit, onCancel: () ->Unit){

    if(openDeleteDialog){
        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = "Delete Diary-Entry") },
            text = { Text(text = "Are you sure you want to delete the diary entry?") },
            confirmButton = {
                Button(onClick = { onDelete() }) {
                    Text(text = "Delete")
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