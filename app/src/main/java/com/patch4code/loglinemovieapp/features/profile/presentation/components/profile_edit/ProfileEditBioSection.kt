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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileEditBioSection - Composable function for editing the bio of the user profile.
 *
 * @author Patch4Code
 */
@Composable
fun ProfileEditBioSection(openEditBioDialog: MutableState<Boolean>, bioText: String?){

    Text(text = stringResource(id = R.string.profile_bio_title), style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
    // button showing the bio text and opening EditBioDialog on click
    ElevatedButton(onClick = { openEditBioDialog.value = true }, modifier = Modifier.fillMaxWidth()) {
        Text(text = bioText ?: "",
            maxLines = 3, minLines = 3, modifier = Modifier.width(250.dp), textAlign = TextAlign.Start
        )
        Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.edit_icon_description), modifier = Modifier.weight(1f))
    }

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}