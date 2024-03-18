package com.patch4code.loglinemovieapp.features.diary.presentation.utils

import android.annotation.SuppressLint
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateHelper {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToLocalDateTime(longValue: Long?): LocalDateTime {
        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(longValue!!), java.time.ZoneId.systemDefault())
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateTimeToLong(dateTime: LocalDateTime): Long{
        return dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun formatDateToDisplay(watchDateTime: LocalDateTime): String {
        return try {
            val dateFormat =  DateTimeFormatter.ofPattern("dd.MM.yyyy")
            watchDateTime.format(dateFormat)
        } catch (e: Exception) {
            Log.e("formatDateToDisplay", "Error formatting date", e)
            "Error formatting date"
        }
    }
}