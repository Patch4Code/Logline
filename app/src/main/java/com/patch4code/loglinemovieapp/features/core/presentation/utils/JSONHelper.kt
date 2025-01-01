package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * JSONHelper - Helper object providing methods for JSON serialization and deserialization
 *
 * @author Patch4Code
 */

object JSONHelper {
    // Gson instance with custom configurations
    // (fix for false conversion of int as double and capability to (de)serialize LocalDateTime with LocalDateTimeAdapter)
    val gson: Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    // Deserializes JSON string to an object
    inline fun <reified T> fromJson(json: String?): T = gson.fromJson(json, T::class.java)

    // Serializes an object into a JSON string
    fun Any.toJson(): String = gson.toJson(this)
}

