package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialMakePublicDialog(openMakePublicDialog: MutableState<Boolean>, socialViewModel: SocialViewModel){

    if(openMakePublicDialog.value){
        AlertDialog(
            onDismissRequest = { openMakePublicDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    openMakePublicDialog.value = false
                    socialViewModel.changeProfileVisibilityState(true) }
                ){
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { openMakePublicDialog.value = false }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Make Profile Public") },
            text = { Text(text = "Are you sure you want to make your Profile public?") }
        )
    }
}