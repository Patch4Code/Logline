package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterTopBarSection - Composable function for displaying a top bar in the filter and sort dialog.
 *
 * @author Patch4Code
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTopBarSection(onCloseDialog:() -> Unit, onResetClick:() -> Unit){

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.sort_filter_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { onCloseDialog() }) {
                Icon(Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_dialog_description)
                )
            }
        },
        actions = {
            TextButton(
                onClick = { onResetClick() },
                content = { Text(stringResource(id = R.string.reset_label)) }
            )
        }
    )
}