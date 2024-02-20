package com.moritz.movieappuitest.views.diary.editelement

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DiaryEditSaveChangesSection(onSaveChanges: () -> Unit, onDiscardChanges: () -> Unit){

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        thickness = 1.dp, color = Color.DarkGray
    )
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,) {
        IconButton(onClick = { onSaveChanges() }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes")
        }
        IconButton(onClick = { onDiscardChanges() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Changes")
        }
    }
}
