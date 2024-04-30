package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryEditDateSection - Composable function representing a section (button) of
 * the DiaryEditElementView for opening a dialog for editing the watch date
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditDateSection(watchDateTime: LocalDateTime, onButtonPressed: () -> Unit){
    TextButton(onClick = { onButtonPressed() }) {
        Text(
            text = "${stringResource(id = R.string.diary_edit_date_section_text)} ${DateHelper.formatDateToDisplay(watchDateTime)}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null
        )
    }
}