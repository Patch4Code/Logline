package com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel

@Composable
fun MakeListPublicDialog(openMakeListPublicDialog: MutableState<Boolean>, listViewModel: ListViewModel){

    if(!openMakeListPublicDialog.value) return

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { openMakeListPublicDialog.value = false },
        confirmButton = {
            Button(onClick = {
                listViewModel.makeListPublic(
                    onSuccess = {publishStatus-> Toast.makeText(context, "List made public ($publishStatus)", Toast.LENGTH_SHORT).show()},
                    onError = {e-> Toast.makeText(context, "Error making List public: ${e.message}", Toast.LENGTH_LONG).show()}
                )
                openMakeListPublicDialog.value = false
            }
            ){
                Text(text = "Make Public")
            }
        },
        dismissButton = {
            Button(onClick = { openMakeListPublicDialog.value = false }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Make this List Public") },
        text = { Text(text = "Are you sure you want to make this List public? " +
                "Please note that if this list has been previously published, the existing version will be overwritten.") }
    )
}