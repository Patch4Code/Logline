package com.moritz.movieappuitest.views.diary.dialogs

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import com.moritz.movieappuitest.utils.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEditDatePickerDialog(watchDate: String, openDatePickerDialog: Boolean, onAccept:(date: String) ->Unit, onCancel: () ->Unit){

    if (openDatePickerDialog){
        val watchDateConverted = DateHelper.convertTimeStringToLong(watchDate)
        val datePickerState = rememberDatePickerState(watchDateConverted)
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

        DatePickerDialog(
            onDismissRequest = { onCancel() },//openDatePickerDialog.value = false
            confirmButton = {
                TextButton(
                    onClick = {
                        var date = ""
                        if(datePickerState.selectedDateMillis != null){
                            date = DateHelper.convertLongToTimeSting(datePickerState.selectedDateMillis)
                        }
                        onAccept(date)
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {//openDatePickerDialog.value = false
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}