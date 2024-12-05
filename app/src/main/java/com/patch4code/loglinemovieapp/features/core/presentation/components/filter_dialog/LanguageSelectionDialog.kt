package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages

@Composable
fun LanguageSelectionDialog(
    showDialog: MutableState<Boolean>,
    selectedLanguages: SnapshotStateList<String>,
    languages: Map<String, String> = MovieLanguages.getAllLanguages()
) {

    if (!showDialog.value) return

    Dialog(onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    text = "Select a Language",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    val sortedLanguages = languages.entries.sortedBy { it.value }
                    sortedLanguages.forEach { language ->
                        item {
                            LanguageItem(
                                language = language,
                                isSelected = selectedLanguages.contains(language.key),
                                onSelect = {
                                    if (selectedLanguages.contains(language.key)) {
                                        selectedLanguages.remove(language.key)
                                    } else {
                                        selectedLanguages.add(language.key)
                                    }
                                }
                            )
                        }
                    }
                }
                Button(onClick = { showDialog.value = false }, modifier = Modifier.fillMaxWidth()
                ){
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun LanguageItem(language: Map.Entry<String, String>, isSelected: Boolean, onSelect: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onSelect).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = language.value, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Selected",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.alpha(if(isSelected) 1.0F else 0.0F)
        )
    }
}