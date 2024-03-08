package com.moritz.movieappuitest.features.diary.presentation.components.dialogs

import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DiaryEditReviewDialog(review: String, openEditReviewDialog: Boolean, onSave:(editedReview: String) ->Unit, onCancel: () ->Unit){

    if(openEditReviewDialog){

        val textInput = remember { mutableStateOf(review) }

        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = "Edit Review") },
            modifier = Modifier.height(400.dp),
            text = {
                OutlinedTextField(
                    value = textInput.value,
                    onValueChange = {textInput.value = it},
                    minLines = 10,
                    maxLines = 10,
                    label = {
                        Text(text = if(review.isEmpty())"Add Review ..." else "Edit Review")
                    }
                )
            },
            confirmButton = {
                Button(onClick = { onSave(textInput.value) }) {
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