package com.patch4code.loglinemovieapp.features.reviews.presentation.components

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewDetailsViewModel

@Composable
fun MakeReviewPublicDialog(openMakeReviewPublicDialog: MutableState<Boolean>, reviewDetailsViewModel: ReviewDetailsViewModel){

    if(!openMakeReviewPublicDialog.value) return

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { openMakeReviewPublicDialog.value = false },
        confirmButton = {
            Button(onClick = {
                reviewDetailsViewModel.makeReviewPublic(
                    onSuccess = {publishStatus-> Toast.makeText(context, "Review made public ($publishStatus)", Toast.LENGTH_SHORT).show() },
                    onError = {e-> Toast.makeText(context, "Error making Review public: ${e.message}", Toast.LENGTH_LONG).show()}
                )
                openMakeReviewPublicDialog.value = false
            }
            ){
                Text(text = "Make Public")
            }
        },
        dismissButton = {
            Button(onClick = { openMakeReviewPublicDialog.value = false }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Make this Review Public") },
        text = { Text(text = "Are you sure you want to make this Review public? " +
                "Please note that if this review has been previously published, the existing version will be overwritten.") }
    )
}