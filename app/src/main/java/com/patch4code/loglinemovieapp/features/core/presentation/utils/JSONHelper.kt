package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.google.gson.Gson

object JSONHelper {
    val gson = Gson()
    inline fun <reified T> fromJson(json: String?): T = gson.fromJson(json, T::class.java)
    fun Any.toJson(): String = Gson().toJson(this)
}