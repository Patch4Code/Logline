package com.patch4code.logline.features.search.presentation.utils

import android.os.Handler
import android.os.Looper
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * KeyboardVisibilityObserver - Composable function that observes the keyboard's visibility state.
 *
 * @author Patch4Code
 */
@Composable
fun KeyboardVisibilityObserver(onKeyboardOpened:()->Unit = {}, onKeyboardClosed:()->Unit){

    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver

    var lastKeyboardState by remember { mutableStateOf(false) }

    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true

            if (isKeyboardOpen) {
                lastKeyboardState = true
                onKeyboardOpened()
            } else if (lastKeyboardState) {
                lastKeyboardState = false

                // Delay to prevent split screen keyboard problems
                Handler(Looper.getMainLooper()).postDelayed({
                    onKeyboardClosed()
                }, 200)
            }
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}