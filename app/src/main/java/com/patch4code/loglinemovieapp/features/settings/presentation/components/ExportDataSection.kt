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
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.ui.theme.Beige

@Composable
fun ExportDataSection(){



    val openExportDataDialog = remember { mutableStateOf(false) }

    HorizontalDivider()
    Column (modifier = Modifier.padding(20.dp)){
        Text(text = "Export Data", style = MaterialTheme.typography.titleMedium, color = Beige)
        Text(text = "Export your Logline App data locally as a ZIP file containing up to three separate .db files. " +
                "Retrieve the file from your downloads folder and utilize it for backup purposes or to transfer your data to another device.",
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "File Name: logline_backup_yyyy-MM-dd_HH-mm-ss.zip", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = { openExportDataDialog.value = true }
        ) {
            Text(text = "Export Data")
        }
    }

    ExportDataDialog(openExportDataDialog)

}