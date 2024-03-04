package com.moritz.movieappuitest.views.list.details.dialogs

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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSettingsBottomSheet(showBottomSheet: Boolean, onClose:()->Unit, onEdit:()->Unit, onDelete:()->Unit){

    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            modifier = Modifier.height(300.dp)
        ){
            Column (modifier = Modifier.padding(16.dp)){
                Text(text = "List Settings", style = MaterialTheme.typography.titleLarge)
                HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                TextButton(onClick = { onEdit() })
                {
                    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start)
                    {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = "Edit List")
                    }
                }
                TextButton(onClick = { onDelete() })
                {
                    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start)
                    {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = "Delete List")
                    }
                }
            }
        }
    }
}