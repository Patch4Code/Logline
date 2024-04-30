package com.patch4code.loglinemovieapp.features.core.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * UiText - Represents UI text that can be either a dynamic string or a string resource
 * This class makes it possible to use string resources outside of composable functions
 *
 * @author Patch4Code
 */

sealed class UiText{
    data class DynamicString(val value: String): UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    // Converts the UiText object to a string (only inside composable functions)
    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    // Converts UiText object to a string using the provided context
    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}