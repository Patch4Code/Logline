package com.patch4code.loglinemovieapp.room_database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

class RoomConverters {

    @TypeConverter
    fun fromMovie(movie: Movie?): String? {
        return Gson().toJson(movie)
    }

    @TypeConverter
    fun toMovie(movieString: String?): Movie? {
        val type = object : TypeToken<Movie?>() {}.type
        return Gson().fromJson(movieString, type)
    }

}