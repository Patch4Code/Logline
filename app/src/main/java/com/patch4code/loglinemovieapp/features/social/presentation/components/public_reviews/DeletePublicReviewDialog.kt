package com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews.PublicReviewDetailsViewModel

@Composable
fun DeletePublicReviewDialog(
    showDeletePublicReviewDialog: MutableState<Boolean>,
    objectId: String,
    navController: NavController,
    publicReviewDetailsViewModel: PublicReviewDetailsViewModel
){

    if(!showDeletePublicReviewDialog.value) return

    val context = LocalContext.current

    val deleteSuccessToastText = stringResource(id = R.string.deleted_success_toast)
    val deleteErrorToastText = stringResource(id = R.string.deleted_error_toast)

    AlertDialog(
        onDismissRequest = { showDeletePublicReviewDialog.value = false },
        confirmButton = {
            Button(onClick = {
                showDeletePublicReviewDialog.value = false
                publicReviewDetailsViewModel.deletePublicReview(
                    objectId = objectId,
                    onSuccess = {
                        Toast.makeText(context, deleteSuccessToastText, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onError = {error->
                        Toast.makeText(context, "$deleteErrorToastText $error", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            ){
                Text(text = stringResource(id = R.string.delete_button_text))
            }
        },
        dismissButton = {
            Button(onClick = { showDeletePublicReviewDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        },
        title = { Text(text = stringResource(id = R.string.deleted_public_review_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.deleted_public_review_dialog_text)) }
    )
}