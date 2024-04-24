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

@Composable
fun SocialMakePublicDialog(openMakePublicDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openMakePublicDialog.value){
        val context = LocalContext.current

        val successToastText = stringResource(id = R.string.success_public_toast)
        val errorToastText = stringResource(id = R.string.error_public_toast)

        AlertDialog(
            onDismissRequest = { openMakePublicDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openMakePublicDialog.value = false
                    socialViewModel.changeProfileVisibilityState(
                        publicState = true,
                        onSuccess = { Toast.makeText(context, successToastText, Toast.LENGTH_SHORT).show() },
                        onError = { Toast.makeText(context, errorToastText, Toast.LENGTH_SHORT).show() }
                    )
                    socialViewModel.updatePublicProfile(context = context, onSuccess = { }, onError = { })
                }
                ){
                    Text(text = stringResource(id = R.string.confirm_button_text))
                }
            },
            dismissButton = {
                Button(onClick = { openMakePublicDialog.value = false }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            },
            title = { Text(text = stringResource(id = R.string.make_public_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.make_public_dialog_text)) }
        )
    }
}