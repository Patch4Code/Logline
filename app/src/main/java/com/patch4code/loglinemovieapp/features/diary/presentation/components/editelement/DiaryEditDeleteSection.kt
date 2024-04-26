package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * DiaryEditDeleteSection - Composable function representing a section (button) of
 * the DiaryEditElementView for opening a dialog to delete the diary entry
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditDeleteSection(onButtonPressed:() -> Unit ){
    TextButton(onClick = { onButtonPressed() }) {
        Text(
            text = stringResource(id = R.string.diary_edit_delete_section_text),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null
        )
    }
}