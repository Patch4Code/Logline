package com.patch4code.loglinemovieapp.features.diary.presentation.components.dialogs

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import java.time.LocalDateTime

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
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}