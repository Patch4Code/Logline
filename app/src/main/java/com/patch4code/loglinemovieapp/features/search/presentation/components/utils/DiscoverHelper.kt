package com.patch4code.loglinemovieapp.features.search.presentation.components.utils

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

object DiscoverHelper {

    fun formatRatingText(start: Float, end: Float): String{
        val roundedStart = start.roundToInt()
        val roundedEnd = end.roundToInt()

        return if (roundedStart == roundedEnd) {
            "Rating ($roundedStart)"
        } else {
            "Rating ($roundedStart-$roundedEnd)"
        }
    }

    fun formatNumber(value: Int?): String {
        return value?.let {
            NumberFormat.getNumberInstance(Locale.US).format(it)
        } ?: "0"
    }
}