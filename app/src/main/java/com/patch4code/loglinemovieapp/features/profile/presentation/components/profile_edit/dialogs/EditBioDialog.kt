package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.dialogs

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileViewModel

@Composable
fun EditBioDialog(openEditBioDialog: MutableState<Boolean>, bioText: String?, profileViewModel: ProfileViewModel){

    if(openEditBioDialog.value){

        val context = LocalContext.current
        val textInput = remember { mutableStateOf(bioText ?: "") }

        AlertDialog(
            onDismissRequest = { openEditBioDialog.value = false  },
            title = { Text(text = "Edit Bio") },
            modifier = Modifier.height(400.dp),
            text = {
                OutlinedTextField(
                    value = textInput.value,
                    onValueChange = {textInput.value = it},
                    minLines = 10,
                    maxLines = 10
                )
            },
            confirmButton = {
                Button(onClick = {
                    openEditBioDialog.value = false
                    profileViewModel.updateBioText(textInput.value)
                    Toast.makeText(context, "Bio was updated", Toast.LENGTH_SHORT).show()

                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openEditBioDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        )

    }
}