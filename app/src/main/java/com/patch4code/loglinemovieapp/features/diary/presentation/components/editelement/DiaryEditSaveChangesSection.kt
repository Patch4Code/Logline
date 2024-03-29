package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun DiaryEditSaveChangesSection(isEdit: Boolean = true, onSaveChanges: () -> Unit, onDiscardChanges: () -> Unit){

    val context = LocalContext.current

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(
            onClick = {
            if (isEdit){ Toast.makeText(context, "Diary Entry updated", Toast.LENGTH_LONG).show() }
            onSaveChanges()
            }
        ){
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes")
        }
        IconButton(onClick = { onDiscardChanges() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Changes")
        }
    }
}
