package com.patch4code.loglinemovieapp.features.search.presentation.components.utils

import android.content.Context
import com.patch4code.loglinemovieapp.features.core.presentation.utils.StringResourceHelper.getStringResourceFromName
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverHelper - Utility object providing helper functions for Discover functionality.
 *
 * @author Patch4Code
 */
object DiscoverHelper {

    fun formatRatingText(start: Float, end: Float, context: Context): String{
        val roundedStart = start.roundToInt()
        val roundedEnd = end.roundToInt()

        return if (roundedStart == roundedEnd) {
            "${getStringResourceFromName(context, "rating_title")} ($roundedStart)"
        } else {
            "${getStringResourceFromName(context, "rating_title")} ($roundedStart-$roundedEnd)"
        }
    }

    fun formatNumber(value: Int?): String {
        return value?.let {
            NumberFormat.getNumberInstance(Locale.US).format(it)
        } ?: "0"
    }
}