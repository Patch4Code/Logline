package com.patch4code.loglinemovieapp.features.core.presentation.utils

import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieHelper - Helper Object providing methods for processing movie-related data
 *
 * @author Patch4Code
 */

object MovieHelper {

    // Extracts year from a release date string
    fun extractYear(releaseDate: String?): String {
        return releaseDate?.takeIf { it.isNotEmpty() }?.split("-")?.get(0) ?: "N/A"
    }

    // Generates the complete poster URL for a movie by combining the TMDB base URL with the URL ending provided by the API
    fun processPosterUrl(posterUrl: String?): String {
        return posterUrl?.takeIf { it.isNotEmpty() }?.let { TmdbCredentials.POSTER_URL + it } ?: ""
    }

    // Formats LocalDateTime object into a list of date components (e.g. [1,JAN,2020])
    fun formatDate(dateTime: LocalDateTime): List<String?> {
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
        return listOf(dateTime.dayOfMonth.toString(), monthMap[dateTime.monthValue], dateTime.year.toString())
    }
}

