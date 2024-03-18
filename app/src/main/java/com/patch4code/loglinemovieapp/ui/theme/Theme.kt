package com.patch4code.loglinemovieapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = LightBlue, // e.g. status Bar ...
    primaryContainer = SapphireBlue, // e.g. Floating Action Button
    secondaryContainer = Charcoal, // e.g. BottomBar and Drawer Elevation
    onSecondaryContainer = AzureishWhite, // e.g. Elevated Icon-color on BottomBar and Drawer

    background = ChineseBlack, // Screen Background color
    onBackground = Beige, // Default Element and Font Color

    surface = ChineseBlack, // Color TopBar, BottomBar and Drawer
    onSurface = Beige, // Element and Font Color on Surface

    outline = PhilippineGray, // e.g. Search-box line
    outlineVariant = SonicSilver, // e.g. Divider Lines
    scrim = Black, // e.g. Drawer background blur color
)


@Composable
fun MovieAppUiTestTheme(
    //dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = darkColorScheme //DarkColorScheme dynamicDarkColorScheme(context)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = Color.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}