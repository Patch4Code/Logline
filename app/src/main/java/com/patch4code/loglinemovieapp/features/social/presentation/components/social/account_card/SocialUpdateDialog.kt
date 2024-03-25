package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialUpdateDialog(openUpdateDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openUpdateDialog.value){
        AlertDialog(
            onDismissRequest = { openUpdateDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openUpdateDialog.value = false
                    socialViewModel.updatePublicProfile() }
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