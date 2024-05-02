package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialUpdateDialog - Composable function that displays a dialog for updating the profile.
 *
 * @author Patch4Code
 */
@Composable
fun SocialUpdateDialog(openUpdateDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openUpdateDialog.value){
        val context = LocalContext.current

        val successToastText = stringResource(id = R.string.update_profile_success_toast)

        AlertDialog(
            onDismissRequest = { openUpdateDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openUpdateDialog.value = false
                    socialViewModel.updatePublicProfile(
                        context = context,
                        onSuccess = { Toast.makeText(context, successToastText, Toast.LENGTH_SHORT).show()},
                        onError = { error-> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
                    )
                }
                ){
                    Text(text = stringResource(id = R.string.confirm_button_text))
                }
            },
            dismissButton = {
                Button(onClick = { openUpdateDialog.value = false }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            },
            title = { Text(text = stringResource(id = R.string.update_profile_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.update_profile_dialog_text)) }
        )
    }
}