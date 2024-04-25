package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.lang.reflect.Type
import java.time.LocalDateTime

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
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

    // Deserializes JSON string to an object based on given type
    inline fun <reified T> fromJsonWithType(json: String?, typeToken: Type): T {
        return gson.fromJson<T>(json, typeToken)
    }

    // Serializes an object into a JSON string
    fun Any.toJson(): String = gson.toJson(this)
}

