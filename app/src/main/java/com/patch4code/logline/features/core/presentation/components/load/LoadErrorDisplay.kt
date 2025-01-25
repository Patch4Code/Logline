package com.patch4code.logline.features.core.presentation.components.load

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import kotlinx.coroutines.delay

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoadErrorDisplay - composable function for displaying a loading indicator
 * followed by an error message with a retry option.
 *
 * @author Patch4Code
 */

@Composable
fun LoadErrorDisplay(
    onReload:()-> Unit,
    initialLoadingDuration: Long = 500L
){
    var showLoadingIndicator by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(initialLoadingDuration)
        showLoadingIndicator = false
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        if (showLoadingIndicator) {
            LoadingIndicator()
        }else{
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = (stringResource(id = R.string.load_error_title)),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.load_error_text),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onReload() }){
                    Text(text = stringResource(id = R.string.try_again_text))
                }
            }
        }
    }
}