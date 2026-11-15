package com.patch4code.logline.features.settings.presentation.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.settings.presentation.utils.SettingsExtensions
import com.patch4code.logline.room_database.LoglineDatabase
import com.patch4code.logline.ui.theme.Beige
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ImportTmdbSection(db: LoglineDatabase) {
    val context = LocalContext.current

    val singleFilePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { localUri ->
            if (localUri != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    SettingsExtensions.importTmdbCsv(
                        context = context,
                        importCsvFileUri = localUri,
                        db = db,
                        onImportSuccess = {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "TMDb Import Successful", Toast.LENGTH_LONG).show()
                            }
                        },
                        onImportError = { errorMsg ->
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                            }
                        }
                    )
                }
            }
        }
    )

    HorizontalDivider()
    Column(modifier = Modifier.padding(20.dp)) {
        Text(text = stringResource(id = R.string.tmdb_import_title), style = MaterialTheme.typography.titleMedium, color = Beige)
        Text(text = stringResource(id = R.string.tmdb_import_text), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = { singleFilePickerLauncher.launch("text/comma-separated-values") }) {
            Text(text = stringResource(id = R.string.tmdb_import_button_text))
        }
    }
}
