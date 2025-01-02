package com.patch4code.loglinemovieapp.features.search.presentation.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.search.presentation.utils.KeyboardVisibilityObserver

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchBar - Composable for displaying a search input field.
 *
 * @author Patch4Code
 */
@Composable
fun SearchBar(
    textInput: MutableState<TextFieldValue>,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onSearch:(String)->Unit
){

    KeyboardVisibilityObserver(onKeyboardClosed = {focusManager.clearFocus()})

    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.Center
    ) {
        // text-field for input of a movie name
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .focusRequester(focusRequester),
            value = textInput.value,
            onValueChange = {textInput.value = it},
            label = { Text(text = stringResource(id = R.string.search_text_field_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(textInput.value.text) }
            ),
            trailingIcon = {
                IconButton(
                    onClick = { onSearch(textInput.value.text) },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_icon_description)
                        )
                    }
                )
            }
        )
    }
}