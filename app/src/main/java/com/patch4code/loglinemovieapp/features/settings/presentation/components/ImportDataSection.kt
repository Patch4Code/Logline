package com.patch4code.loglinemovieapp.features.settings.presentation.components

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.settings.presentation.utils.SettingsExtensions
import com.patch4code.loglinemovieapp.ui.theme.Beige

@Composable
fun ImportDataSection(){

    val context = LocalContext.current

    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { localUri ->
            if(localUri != null){
                Log.e("ImportDataSection", "uri: $localUri")
                SettingsExtensions.importDatabaseFile(context, localUri,
                    onImportSuccess = {
                        Log.e("ImportDataSection", "onImportSuccess")
                        val packageManager: PackageManager = context.packageManager
                        val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
                        val componentName: ComponentName = intent.component!!
                        val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
                        context.startActivity(restartIntent)
                        Runtime.getRuntime().exit(0)
                    }
                )
            }
        }
    )

    HorizontalDivider()
    Column (modifier = Modifier.padding(20.dp)){
        Text(text = "Import Database", style = MaterialTheme.typography.titleMedium, color = Beige)
        Text(text = "Import a previously exported ZIP file, to restore backed-up data. " +
                "The import file should be a ZIP archive containing up to 3 .db files." +
                "This Process will restart your app.",
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = Icons.Default.WarningAmber, contentDescription = "Warning")
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Please note that the current app data will be permanently replaced after the import process is completed!",
                style = MaterialTheme.typography.labelMedium)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = { singleFilePickerLauncher.launch("application/zip") }) {
            Text(text = "Import Data")
        }
    }
}