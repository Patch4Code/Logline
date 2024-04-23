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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileViewModel

@Composable
fun EditBioDialog(openEditBioDialog: MutableState<Boolean>, bioText: String?, profileViewModel: ProfileViewModel){

    if(openEditBioDialog.value){

        val context = LocalContext.current
        val textInput = remember { mutableStateOf(bioText ?: "") }

        val toastTest = stringResource(id = R.string.bio_updated_toast)

        AlertDialog(
            onDismissRequest = { openEditBioDialog.value = false  },
            title = { Text(text = stringResource(id = R.string.edit_bio_text)) },
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
                    Toast.makeText(context, toastTest, Toast.LENGTH_SHORT).show()

                }) {
                    Text(text = stringResource(id = R.string.save_button_text))
                }
            },
            dismissButton = {
                Button(onClick = {
                    openEditBioDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            }
        )
    }
}