package com.moritz.movieappuitest.features.diary.presentation.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateHelper {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTimeSting(dateMillis: Long?): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(Date(dateMillis!!))
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTimeStringToLong(timeString: String): Long{
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val date = formatter.parse(timeString)
            date?.time ?: 0L
        } catch (e: Exception) {
            Log.e("convertTimeStringToLong", "error during parsing of timeString to Long", e)
            0L
        }
    }

    fun formatDateToDisplay(dateString: String): String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(dateString)
            val displayFormat = SimpleDateFormat("d. MMMM yyyy", Locale.getDefault())
            displayFormat.format(date ?: Date(0))
        } catch (e: Exception) {
            Log.e("formatDateToDisplay", "Error formatting date", e)
            "Error formatting date"
        }
    }
}