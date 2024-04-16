package com.patch4code.loglinemovieapp.features.social.presentation.components.public_reviews

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
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

    AlertDialog(
        onDismissRequest = { showDeletePublicReviewDialog.value = false },
        confirmButton = {
            Button(onClick = {
                showDeletePublicReviewDialog.value = false
                publicReviewDetailsViewModel.deletePublicReview(
                    objectId = objectId,
                    onSuccess = {
                        Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onError = {error->
                        Toast.makeText(context, "Delete Error: $error", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            ){
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(onClick = { showDeletePublicReviewDialog.value = false }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete this public review") },
        text = { Text(text = "Are you sure you want to delete this public review?" +
                "Deleting this public review will have no effect on locally saved reviews.") }
    )
}