package com.patch4code.logline.features.core.presentation.components.modifier

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * setPaddingBasedOnApiLvl - Helper Function to set the right padding
 *
 * The padding is dynamically determined based on the current API level
 * to account for differences in navigation bar behavior and layout handling:
 *
 * - On On Android 14 (API level 34) and lower, your app's UI does not draw underneath
 *   the system bars and display cutouts by default.
 *
 * - On Android 15 (API level 35) and higher, your app draws underneath the system bars
 *   and display cutouts once your app targets SDK 35.
 *
 * Therefore a different handling of (bottom) padding is needed here
 *
 * see: https://developer.android.com/develop/ui/compose/layouts/insets
 *
 * @author Patch4Code
 */
@Composable
fun setPaddingBasedOnApiLvl(edgeToEdgePadding: Dp, legacyPadding: Dp = 0.dp): Dp {

    val currentApiLevel = Build.VERSION.SDK_INT

    return if (currentApiLevel >= 35) {
        edgeToEdgePadding  // Increased padding for newer system behaviors
    } else {
        legacyPadding // Standard padding for older Android versions
    }
}