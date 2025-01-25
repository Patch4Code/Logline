package com.patch4code.logline.features.diary.presentation.components.dialogs

import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryEditReviewDialog - Composable function representing a dialog for editing or adding a review in the diary edit section.
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditReviewDialog(review: String, openEditReviewDialog: Boolean, onSave:(editedReview: String) ->Unit, onCancel: () ->Unit){

    if(openEditReviewDialog){

        val textInput = remember { mutableStateOf(review) }

        AlertDialog(
            onDismissRequest = { onCancel() },
            title = { Text(text = stringResource(id = R.string.diary_edit_review_dialog_title)) },
            modifier = Modifier.height(400.dp),
            text = {
                OutlinedTextField(
                    value = textInput.value,
                    onValueChange = {textInput.value = it},
                    minLines = 10,
                    maxLines = 10,
                    label = {
                        Text(text = if(review.isEmpty()) stringResource(id = R.string.diary_edit_review_dialog_label_empty)
                            else stringResource(id = R.string.diary_edit_review_dialog_label)
                        )
                    }
                )
            },
            confirmButton = {
                Button(onClick = { onSave(textInput.value) }) {
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