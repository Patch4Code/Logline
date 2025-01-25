package com.patch4code.logline.features.settings.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.ui.theme.Beige

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ExportDataSection - Composable function to display the section for exporting data.
 *
 * @author Patch4Code
 */
@Composable
fun ExportDataSection(){

    val openExportDataDialog = remember { mutableStateOf(false) }

    HorizontalDivider()
    Column (modifier = Modifier.padding(20.dp)){
        // Info text
        Text(text = stringResource(id = R.string.export_title), style = MaterialTheme.typography.titleMedium, color = Beige)
        Text(text = stringResource(id = R.string.export_text),
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.export_file_title), style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        // Button to open export dialog
        Button(onClick = { openExportDataDialog.value = true }
        ) {
            Text(text = stringResource(id = R.string.export_title))
        }
    }
    ExportDataDialog(openExportDataDialog)
}