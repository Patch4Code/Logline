package com.patch4code.logline.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.components.modifier.setPaddingBasedOnApiLvl

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterApplyButton - Composable function representing a button for applying filters.
 *
 * @author Patch4Code
 */
@Composable
fun FilterApplyButton(onClick:() -> Unit){

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
            .padding(bottom = setPaddingBasedOnApiLvl(86.dp, 32.dp)),
        onClick = { onClick() },
        content = { Text(stringResource(id = R.string.apply_filters_label)) }
    )
}