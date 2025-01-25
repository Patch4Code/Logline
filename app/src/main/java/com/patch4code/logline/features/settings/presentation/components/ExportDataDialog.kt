package com.patch4code.logline.features.settings.presentation.components

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.settings.presentation.utils.SettingsExtensions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ExportDataDialog - Composable function to display a dialog for exporting the app data.
 * Calls corresponding function in SettingsExtensions on confirm.
 *
 * @author Patch4Code
 */
@Composable
fun ExportDataDialog(openExportDataDialog: MutableState<Boolean>){

    if(!openExportDataDialog.value) return

    val context = LocalContext.current

    val toastText = stringResource(id = R.string.export_data_toast)

    AlertDialog(
        onDismissRequest = { openExportDataDialog.value = false },
        title = { Text(text = stringResource(id = R.string.export_data_dialog_title))},
        text = { Text(text = stringResource(id = R.string.export_data_dialog_text))},
        confirmButton = {
            Button(onClick = {
                openExportDataDialog.value = false
                SettingsExtensions.exportDatabaseFile(context)
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }) {
                Text(text = stringResource(id = R.string.export_button_text))
            }
        },
        dismissButton = {
            Button(onClick = { openExportDataDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        }
    )
}