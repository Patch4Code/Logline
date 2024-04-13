package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.lang.reflect.Type

object JSONHelper {
    val gson: Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .create()
    inline fun <reified T> fromJson(json: String?): T = gson.fromJson(json, T::class.java)

    inline fun <reified T> fromJsonWithType(json: String?, typeToken: Type): T {
        return gson.fromJson<T>(json, typeToken)
    }

    fun Any.toJson(): String = gson.toJson(this)
}

