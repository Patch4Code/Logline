package com.patch4code.loglinemovieapp.features.core.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

//This class makes it possible to use string resources outside of composable functions
sealed class UiText{
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    @Composable
    fun asString(): String {
        return when(this) {
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is StringResource -> context.getString(resId, *args)
        }
    }
}
