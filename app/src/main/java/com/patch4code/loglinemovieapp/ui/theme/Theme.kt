package com.patch4code.loglinemovieapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    onPrimary = DarkMidnightBlue,
    primaryContainer = CadetBlue,
    onPrimaryContainer = AliceBlue,
    inversePrimary = DarkCerulean,
    secondary = LightSteelBlue,
    onSecondary = DarkSlateGray1,
    secondaryContainer = Arsenic,
    onSecondaryContainer = LightSkyBlue,
    tertiary = Thistle,
    onTertiary = DarkByzantium,
    tertiaryContainer = DavysGrey,
    onTertiaryContainer = Lavender,
    background = EerieBlack,
    onBackground = LavenderBlush,
    surface = EerieBlack,
    onSurface = LavenderBlush,
    error = LightCoral,
    onError = DarkCandyAppleRed,
    errorContainer = RustyRed,
    onErrorContainer = PinkLace,
    outline = CoolGrey,
    outlineVariant = SonicSilver,
    scrim = Black,
    surfaceBright = DarkSlateGray2,
    surfaceDim = EerieBlack,
    surfaceContainer = RaisinBlack,
    surfaceContainerHigh = OuterSpace,
    surfaceContainerHighest = Jet,
    surfaceContainerLow = SmokyBlack,
    surfaceContainerLowest = RichBlack
)


@Composable
fun MovieAppUiTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = DarkColorScheme //DarkColorScheme dynamicDarkColorScheme(context)

    //Log.e("Theme", "$colorScheme")

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