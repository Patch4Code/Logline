package com.patch4code.loglinemovieapp.features.search.presentation.components.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object TextInputSaver {
    val saver = Saver<TextFieldValue, Pair<String, Int>>(
        save = { textFieldValue ->
            textFieldValue.text to textFieldValue.selection.end
        },
        restore = { (text, selection) ->
            TextFieldValue(text = text, selection = TextRange(selection))
        }
    )
}