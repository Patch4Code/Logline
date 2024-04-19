package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

@Composable
fun ProfileEditNameSection(openEditProfileNameDialog: MutableState<Boolean>, userName: String?){

    val context = LocalContext.current
    val dataLoginStore = remember { StoreUserData(context) }
    val loginUserName = dataLoginStore.getUsername.collectAsState(initial = "").value
    val isLoggedIn = !loginUserName.isNullOrEmpty()

    Text(text = "Profile Name", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
    ElevatedButton(
        onClick = { openEditProfileNameDialog.value = true },
        enabled = !isLoggedIn
    ) {
        Text(text = (if(isLoggedIn) loginUserName!! else userName ?: "Anonymous"))
        Spacer(modifier = Modifier.padding(4.dp))
        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
    }

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}