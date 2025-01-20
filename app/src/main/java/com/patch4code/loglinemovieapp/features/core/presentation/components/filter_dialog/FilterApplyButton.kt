package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R

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
            .padding(calculateBottomPaddingBasedOnApiLvl()),
        onClick = { onClick() },
        content = { Text(stringResource(id = R.string.apply_filters_label)) }
    )
}

@Composable
private fun calculateBottomPaddingBasedOnApiLvl(): Dp {
    // Explanation:
    // The bottom padding is dynamically determined based on the current API level
    // to account for differences in navigation bar behavior and layout handling:
    //
    // - On On Android 14 (API level 34) and lower, your app's UI does not draw underneath
    //   the system bars and display cutouts by default.
    // - On Android 15 (API level 35) and higher, your app draws underneath the system bars
    //   and display cutouts once your app targets SDK 35.
    //
    // Therefore a different handling of bottom padding is needed here
    //
    // see: https://developer.android.com/develop/ui/compose/layouts/insets

    val currentApiLevel = Build.VERSION.SDK_INT

    return if (currentApiLevel >= 35) {
       86.dp  // Increased padding for newer system behaviors
    } else {
        32.dp // Standard padding for older Android versions
    }
}