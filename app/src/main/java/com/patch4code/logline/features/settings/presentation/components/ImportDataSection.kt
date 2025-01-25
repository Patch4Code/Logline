package com.patch4code.logline.features.settings.presentation.components

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.settings.presentation.utils.SettingsExtensions
import com.patch4code.logline.ui.theme.Beige

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ImportDataSection - Composable function to display the section for importing data.
 * Uses Activity result launcher for picking a single zip-file from the device.
 *
 * @author Patch4Code
 */
@Composable
fun ImportDataSection(){

    val context = LocalContext.current

    // Activity result launcher for picking a single file
    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { localUri ->
            if(localUri != null){
                Log.e("ImportDataSection", "uri: $localUri")
                SettingsExtensions.importDatabaseFile(context, localUri,
                    onImportSuccess = {
                        //Log.e("ImportDataSection", "onImportSuccess")
                        // restarts the app on import success
                        val packageManager: PackageManager = context.packageManager
                        val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
                        val componentName: ComponentName = intent.component!!
                        val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
                        context.startActivity(restartIntent)
                        Runtime.getRuntime().exit(0)
                    },
                    onImportError = {errorMsg ->
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    )

    HorizontalDivider()
    Column (modifier = Modifier.padding(20.dp)){
        // info text
        Text(text = stringResource(id = R.string.import_title), style = MaterialTheme.typography.titleMedium, color = Beige)
        Text(text = stringResource(id = R.string.import_text),
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = Icons.Default.WarningAmber, contentDescription = stringResource(id = R.string.warning_icon_description))
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = stringResource(id = R.string.import_warning_text),
                style = MaterialTheme.typography.labelMedium)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        // button that launches Activity result launcher and sets the file-typ to zip
        Button(onClick = { singleFilePickerLauncher.launch("application/zip") }) {
            Text(text = stringResource(id = R.string.import_button_text))
        }
    }
}