package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ProfileEditBioSection(openEditBioDialog: MutableState<Boolean>, bioText: String?){

    Text(text = "Bio", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
    ElevatedButton(onClick = { openEditBioDialog.value = true }, modifier = Modifier.fillMaxWidth()) {
        Text(text = bioText ?: "",
            maxLines = 3, minLines = 3, modifier = Modifier.width(250.dp), textAlign = TextAlign.Start
        )
        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.weight(1f))
    }

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}