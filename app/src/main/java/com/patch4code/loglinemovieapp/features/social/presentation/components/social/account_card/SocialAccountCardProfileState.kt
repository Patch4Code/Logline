package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.PublicOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialAccountCardProfileState(savedLoginData: StoreUserData, socialViewModel: SocialViewModel){

    val openMakePrivateDialog = remember { mutableStateOf(false)  }
    val openMakePublicDialog = remember { mutableStateOf(false)  }
    val openUpdateDialog = remember { mutableStateOf(false)  }

    Row (verticalAlignment = Alignment.CenterVertically){
        if(savedLoginData.getIsProfilePublic.collectAsState(initial = false).value == true){
            Text(text = "Profile is Public", Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)

            TextButton(onClick = { openUpdateDialog.value = true }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }

            TextButton(onClick = { openMakePrivateDialog.value = true }) {
                Icon(imageVector = Icons.Default.Public, contentDescription = null)
            }
        }else{
            Text(text = "Profile is Private", Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { openMakePublicDialog.value = true }) {
                Icon(imageVector = Icons.Default.PublicOff, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
    SocialMakePrivateDialog(openMakePrivateDialog, socialViewModel)
    SocialMakePublicDialog(openMakePublicDialog, socialViewModel)
    SocialUpdateDialog(openUpdateDialog, socialViewModel)
}