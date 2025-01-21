package com.patch4code.loglinemovieapp.ui.theme.utils

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

object SystemUiUtils {

    fun setBarColors(window: Window, view: View, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+

            // From Android 15 (API level 35), system bars default to drawing content underneath.
            // Adjusting appearance here ensures text color matches the background color.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

        } else {
            // For Android 14 and below
            window.statusBarColor = color
            window.navigationBarColor = Color.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }
}