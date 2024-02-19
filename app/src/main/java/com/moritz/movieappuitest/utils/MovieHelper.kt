package com.moritz.movieappuitest.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object MovieHelper {

    fun extractYear(releaseDate: String?): String {
        return releaseDate?.takeIf { it.isNotEmpty() }?.split("-")?.get(0) ?: "N/A"
    }

    fun processPosterUrl(posterUrl: String?): String {
        return posterUrl?.takeIf { it.isNotEmpty() }?.let { TmdbCredentials.POSTER_URL + it } ?: ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String): List<String?> {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)

        val monthMap = mapOf(
            1 to "JAN",
            2 to "FEB",
            3 to "MAR",
            4 to "APR",
            5 to "MAY",
            6 to "JUN",
            7 to "JUL",
            8 to "AUG",
            9 to "SEP",
            10 to "OCT",
            11 to "NOV",
            12 to "DEC"
        )
        Log.e("Date",date.toString())

        return listOf(date.dayOfMonth.toString(), monthMap[date.monthValue], date.year.toString())//date.format(formatter)
    }
}

