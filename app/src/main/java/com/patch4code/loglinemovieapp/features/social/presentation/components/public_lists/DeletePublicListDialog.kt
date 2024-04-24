package com.patch4code.loglinemovieapp.features.social.presentation.components.public_lists

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
    val deleteSuccessToastText = stringResource(id = R.string.deleted_success_toast)
    val deleteErrorToastText = stringResource(id = R.string.deleted_error_toast)

    AlertDialog(
        onDismissRequest = { showDeletePublicListDialog.value = false },
        confirmButton = {
            Button(onClick = {
                showDeletePublicListDialog.value = false
                publicListViewModel.deletePublicList(
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
            Button(onClick = { showDeletePublicListDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        },
        title = { Text(text = stringResource(id = R.string.deleted_public_list_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.deleted_public_list_dialog_text)) }
    )
}