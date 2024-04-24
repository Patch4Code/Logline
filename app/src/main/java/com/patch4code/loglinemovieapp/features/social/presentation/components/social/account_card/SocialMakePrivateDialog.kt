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
fun SocialMakePrivateDialog(openMakePrivateDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openMakePrivateDialog.value){
        val context = LocalContext.current

        val successToastText = stringResource(id = R.string.success_private_toast)
        val errorToastText = stringResource(id = R.string.error_private_toast)

        AlertDialog(
            onDismissRequest = { openMakePrivateDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openMakePrivateDialog.value = false
                    socialViewModel.changeProfileVisibilityState(
                        publicState = false,
                        onSuccess = { Toast.makeText(context, successToastText, Toast.LENGTH_SHORT).show() },
                        onError = { Toast.makeText(context, errorToastText, Toast.LENGTH_SHORT).show() }
                    )
                }
                ){
                    Text(text = stringResource(id = R.string.confirm_button_text))
                }
            },
            dismissButton = {
                Button(onClick = { openMakePrivateDialog.value = false }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            },
            title = { Text(text = stringResource(id = R.string.make_private_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.make_private_dialog_text)) }
        )
    }
}