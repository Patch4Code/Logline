package com.patch4code.logline.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import com.patch4code.logline.ui.theme.utils.SystemUiUtils

private val darkColorScheme = darkColorScheme(
    primary = LightBlue, // e.g. status Bar
    primaryContainer = SapphireBlue, // e.g. Floating Action Button
    secondaryContainer = Charcoal, // e.g. BottomBar and Drawer Elevation
    onSecondaryContainer = AzureishWhite, // e.g. Elevated Icon-color on BottomBar and Drawer

    background = ChineseBlack, // Screen Background color
    onBackground = Beige, // Default Element and Font Color

    surface = ChineseBlack, // Color TopBar not scrolled
    onSurface = Beige, // Element and Font Color on Surface
    surfaceContainer = MidnightBlue, // e.g. BottomBar, TopBar scrolled
    surfaceContainerLow = MidnightBlue, // e.g. Drawer color
    surfaceContainerHigh = MidnightBlue, //dialog popup color bg

    outline = PhilippineGray, // e.g. Search-box line
    outlineVariant = SonicSilver, // e.g. Divider Lines
    scrim = Black, // e.g. Drawer background blur color
)

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieAppUiTestTheme - Composable function that applies custom a dark theme for the App.
 *
 * @author Patch4Code
 */
@Composable
fun LoglineUiTheme(
    //dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = darkColorScheme //DarkColorScheme dynamicDarkColorScheme(context)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            SystemUiUtils.setBarColors(window, view, colorScheme.primary.toArgb())
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}