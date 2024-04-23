package com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel

@Composable
fun MakeListPublicDialog(openMakeListPublicDialog: MutableState<Boolean>, listViewModel: ListViewModel){

    if(!openMakeListPublicDialog.value) return

    val context = LocalContext.current
    val successToastText = stringResource(id = R.string.list_publish_dialog_success_toast)
    val errorToastText = stringResource(id = R.string.list_publish_dialog_error_toast)

    AlertDialog(
        onDismissRequest = { openMakeListPublicDialog.value = false },
        confirmButton = {
            Button(onClick = {
                listViewModel.makeListPublic(
                    onSuccess = {publishStatus-> Toast.makeText(context, "$successToastText ($publishStatus)", Toast.LENGTH_SHORT).show()},
                    onError = {e-> Toast.makeText(context, "$errorToastText ${e.message}", Toast.LENGTH_LONG).show()}
                )
                openMakeListPublicDialog.value = false
            }
            ){
                Text(text = stringResource(id = R.string.list_make_public_text))
            }
        },
        dismissButton = {
            Button(onClick = { openMakeListPublicDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        },
        title = { Text(text = stringResource(id = R.string.list_publish_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.list_publish_dialog_text)) }
    )
}