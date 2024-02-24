package com.moritz.movieappuitest.views.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DiaryEditDiscardDialog(openDiscardDialog: Boolean, onDiscard:() ->Unit, onCancel: () ->Unit){

    if (openDiscardDialog) {
        AlertDialog(
            onDismissRequest = { onCancel() },//openDiscardDialog.value = false
            title = { Text(text = "Discard Changes?") },
            text = { Text(text = "When you continue, changes will be lost.") },
            confirmButton = {
                Button(onClick = { onDiscard() }) {
                    Text(text = "Discard")
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