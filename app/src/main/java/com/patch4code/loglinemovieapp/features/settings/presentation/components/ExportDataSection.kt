package com.patch4code.loglinemovieapp.features.settings.presentation.components

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
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.ui.theme.Beige

@Composable
fun ExportDataSection(){

    val openExportDataDialog = remember { mutableStateOf(false) }

    HorizontalDivider()
    Column (modifier = Modifier.padding(20.dp)){
        Text(text = stringResource(id = R.string.export_title), style = MaterialTheme.typography.titleMedium, color = Beige)
        Text(text = stringResource(id = R.string.export_text),
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.export_file_title), style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = { openExportDataDialog.value = true }
        ) {
            Text(text = stringResource(id = R.string.export_title))
        }
    }
    ExportDataDialog(openExportDataDialog)
}