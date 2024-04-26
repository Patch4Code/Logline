package com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import java.time.LocalDateTime

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * DiaryEditDatePickerDialog - Composable function representing a date picker dialog for the diary edit section.
 * Uses Material3 DatePickerDialog
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEditDatePickerDialog(watchDateTime: LocalDateTime, openDatePickerDialog: Boolean, onAccept:(dateTime: LocalDateTime) ->Unit, onCancel: () ->Unit){

    if (openDatePickerDialog){
        val watchDateConverted = DateHelper.convertDateTimeToLong(watchDateTime)
        val datePickerState = rememberDatePickerState(watchDateConverted)
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

        DatePickerDialog(
            onDismissRequest = { onCancel() },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(datePickerState.selectedDateMillis != null){
                            val dateTime = DateHelper.convertLongToLocalDateTime(datePickerState.selectedDateMillis)
                            onAccept(dateTime)
                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = stringResource(id = R.string.ok_button_text))
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}