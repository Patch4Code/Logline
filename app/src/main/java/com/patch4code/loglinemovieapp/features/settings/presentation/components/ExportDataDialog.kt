package com.patch4code.loglinemovieapp.features.settings.presentation.components

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.settings.presentation.utils.SettingsExtensions

@Composable
fun ExportDataDialog(openExportDataDialog: MutableState<Boolean>){

    if(!openExportDataDialog.value) return

    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { openExportDataDialog.value = false },
        title = { Text(text = "Export App Data")},
        text = { Text(text = "Export your App data as backup or to transfer it to another device. " +
                "You can find the exported zip file in the download folder of your device.")},
        confirmButton = {
            Button(onClick = {
                openExportDataDialog.value = false
                SettingsExtensions.exportDatabaseFile(context)
                Toast.makeText(context, "Data was exported", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Export")
            }
        },
        dismissButton = {
            Button(onClick = { openExportDataDialog.value = false }) {
                Text(text = "Cancel")
            }
        }
    )

}