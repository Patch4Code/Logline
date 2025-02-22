package com.patch4code.logline.features.list.presentation.components.list.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListSettingsBottomSheet - Composable function representing a bottom sheet for list settings.
 * Contains button to delete and to edit the list.
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSettingsBottomSheet(showBottomSheet: Boolean, onClose:()->Unit, onEdit:()->Unit, onDelete:()->Unit){

    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = { onClose() },
        ){
            Column (modifier = Modifier.height(200.dp).padding(16.dp)){
                Text(text = stringResource(id = R.string.list_bottom_sheet_title), style = MaterialTheme.typography.titleLarge)
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                TextButton(onClick = { onEdit() })
                {
                    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start)
                    {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = stringResource(id = R.string.edit_list_text))
                    }
                }
                TextButton(onClick = { onDelete() })
                {
                    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start)
                    {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = stringResource(id = R.string.delete_list_text))
                    }
                }
            }
        }
    }
}