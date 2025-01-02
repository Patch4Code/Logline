package com.patch4code.loglinemovieapp.features.diary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * EmptyDiaryText - Composable function that displays a message when the diary is empty.
 *
 * @author Patch4Code
 */
@Composable
fun EmptyDiaryText(filtersActive: Boolean){

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text =
            if(filtersActive)
                stringResource(id = R.string.diary_no_filter_match_text)
            else
                stringResource(id = R.string.diary_empty_text),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

}