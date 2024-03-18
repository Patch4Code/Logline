package com.patch4code.loglinemovieapp.features.diary.presentation.utils

import android.annotation.SuppressLint
import android.util.Log
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateHelper {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToLocalDateTime(longValue: Long?): LocalDateTime {
        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(longValue!!), java.time.ZoneId.systemDefault())
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateTimeToLong(dateTime: LocalDateTime): Long{
        Log.e("DateHelper", "convertDateTimeToLong: $dateTime, ${dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()}")
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    fun formatDateToDisplay(watchDateTime: LocalDateTime): String {
        val dateFormat =  DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return watchDateTime.format(dateFormat)
    }
}