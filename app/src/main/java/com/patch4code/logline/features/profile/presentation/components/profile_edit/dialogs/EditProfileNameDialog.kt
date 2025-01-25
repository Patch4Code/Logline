package com.patch4code.logline.features.profile.presentation.components.profile_edit.dialogs

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
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.profile.presentation.screen_profile.ProfileViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * EditProfileNameDialog - Composable function that displays the edit profile name dialog.
 *
 * @author Patch4Code
 */
@Composable
fun EditProfileNameDialog(openEditProfileNameDialog: MutableState<Boolean>, profileName: String?, profileViewModel: ProfileViewModel){

    if(openEditProfileNameDialog.value){

        val context = LocalContext.current
        val textInput = remember { mutableStateOf(profileName ?: "") }

        val toastText = stringResource(id = R.string.name_updated_toast)

        AlertDialog(
            onDismissRequest = { openEditProfileNameDialog.value = false  },
            title = { Text(text = stringResource(id = R.string.edit_name_title)) },
            text = {
                OutlinedTextField(
                    value = textInput.value,
                    onValueChange = {textInput.value = it},
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    openEditProfileNameDialog.value = false
                    profileViewModel.updateProfileName(textInput.value)
                    Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()

                }) {
                    Text(text = stringResource(id = R.string.save_button_text))
                }
            },
            dismissButton = {
                Button(onClick = {
                    openEditProfileNameDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            }
        )
    }
}