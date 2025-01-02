package com.patch4code.loglinemovieapp.features.search.presentation.components.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TextInputSaver - Provides a `Saver` implementation for saving and restoring
 * `TextFieldValue` state in a Compose environment.
 *
 * @author Patch4Code
 */
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