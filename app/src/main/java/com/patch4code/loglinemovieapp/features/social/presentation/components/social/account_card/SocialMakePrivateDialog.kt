package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialMakePrivateDialog(openMakePrivateDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openMakePrivateDialog.value){
        AlertDialog(
            onDismissRequest = { openMakePrivateDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openMakePrivateDialog.value = false
                    socialViewModel.changeProfileVisibilityState(false) }
                ){
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { openMakePrivateDialog.value = false }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Make Profile Private") },
            text = { Text(text = "Are you sure you want to make your Profile private?") }
        )
    }
}