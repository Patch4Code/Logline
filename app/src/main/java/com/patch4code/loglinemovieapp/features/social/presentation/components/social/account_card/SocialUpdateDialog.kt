package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialUpdateDialog(openUpdateDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openUpdateDialog.value){
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { openUpdateDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openUpdateDialog.value = false
                    socialViewModel.updatePublicProfile(
                        context = context,
                        onSuccess = { Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()},
                        onError = { error-> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
                    )
                }
                ){
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { openUpdateDialog.value = false }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Update Public Profile") },
            text = { Text(text = "Do you want to update your Profile? Please only do this when you made changes to your Profile-Page.") }
        )
    }
}