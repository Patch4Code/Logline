package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.dialogs

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileViewModel

@Composable
fun EditProfileNameDialog(openEditProfileNameDialog: MutableState<Boolean>, profileName: String?, profileViewModel: ProfileViewModel){

    if(openEditProfileNameDialog.value){

        val context = LocalContext.current
        val textInput = remember { mutableStateOf(profileName ?: "") }

        AlertDialog(
            onDismissRequest = { openEditProfileNameDialog.value = false  },
            title = { Text(text = "Edit Profile Name") },
            text = {
                OutlinedTextField(
                    value = textInput.value,
                    onValueChange = {textInput.value = it}
                )
            },
            confirmButton = {
                Button(onClick = {
                    openEditProfileNameDialog.value = false
                    profileViewModel.updateProfileName(textInput.value)
                    Toast.makeText(context, "Profile Name was updated", Toast.LENGTH_SHORT).show()

                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openEditProfileNameDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}