package com.patch4code.loglinemovieapp.features.core.presentation.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * DateHelper - Helper object providing methods for data and time conversions
 *
 * @author Patch4Code
 */

object DateHelper {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToLocalDateTime(longValue: Long?): LocalDateTime {
        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(longValue!!), java.time.ZoneId.systemDefault())
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateTimeToLong(dateTime: LocalDateTime): Long{
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    fun formatDateToDisplay(watchDateTime: LocalDateTime): String {
        val dateFormat =  DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return watchDateTime.format(dateFormat)
    }
}