package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import java.time.LocalDateTime

@Composable
fun DiaryEditDateSection(watchDateTime: LocalDateTime, onButtonPressed: () -> Unit){
    TextButton(onClick = { onButtonPressed() }) {
        Text(
            text = "Watched ${DateHelper.formatDateToDisplay(watchDateTime)}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null
        )
    }
}