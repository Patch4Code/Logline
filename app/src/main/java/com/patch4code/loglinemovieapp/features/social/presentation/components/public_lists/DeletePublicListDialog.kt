package com.patch4code.loglinemovieapp.features.social.presentation.components.public_lists

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.social.presentation.screen_public_lists.PublicListViewModel

@Composable
fun DeletePublicListDialog(
    showDeletePublicListDialog: MutableState<Boolean>,
    objectId: String,
    navController: NavController,
    publicListViewModel: PublicListViewModel
){

    if(!showDeletePublicListDialog.value) return

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { showDeletePublicListDialog.value = false },
        confirmButton = {
            Button(onClick = {
                showDeletePublicListDialog.value = false
                publicListViewModel.deletePublicList(
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
            Button(onClick = { showDeletePublicListDialog.value = false }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete this public list") },
        text = { Text(text = "Are you sure you want to delete this public list?" +
                "Deleting this public list will have no effect on locally saved lists.") }
    )
}