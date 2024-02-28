package com.moritz.movieappuitest.views.list.details

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteMovieFromListDialog(openDeleteMovieDialog: Boolean, onDelete:()->Unit, onCancel:()->Unit){

    if(openDeleteMovieDialog){
        AlertDialog(
            onDismissRequest = { onCancel() },//openDiscardDialog.value = false
            title = { Text(text = "Delete movie from List") },
            text = { Text(text = "Are you sure you want to delete the movie from the list?") },
            confirmButton = {
                Button(onClick = { onDelete() }) {
                    Text(text = "Delete Movie")
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