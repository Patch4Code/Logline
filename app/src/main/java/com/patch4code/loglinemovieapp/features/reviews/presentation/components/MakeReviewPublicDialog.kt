package com.patch4code.loglinemovieapp.features.reviews.presentation.components

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewDetailsViewModel

@Composable
fun MakeReviewPublicDialog(openMakeReviewPublicDialog: MutableState<Boolean>, reviewDetailsViewModel: ReviewDetailsViewModel){

    if(!openMakeReviewPublicDialog.value) return

    val context = LocalContext.current

    val toastSuccessText = stringResource(id = R.string.review_publish_success_toast)
    val toastErrorText = stringResource(id = R.string.review_publish_error_toast)

            AlertDialog(
        onDismissRequest = { openMakeReviewPublicDialog.value = false },
        confirmButton = {
            Button(onClick = {
                reviewDetailsViewModel.makeReviewPublic(
                    onSuccess = {publishStatus-> Toast.makeText(context, "$toastSuccessText ($publishStatus)", Toast.LENGTH_SHORT).show() },
                    onError = {e-> Toast.makeText(context, "$toastErrorText ${e.message}", Toast.LENGTH_LONG).show()}
                )
                openMakeReviewPublicDialog.value = false
            }
            ){
                Text(text = stringResource(id = R.string.make_public_text))
            }
        },
        dismissButton = {
            Button(onClick = { openMakeReviewPublicDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        },
        title = { Text(text = stringResource(id = R.string.review_publish_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.review_publish_dialog_text)) }
    )
}