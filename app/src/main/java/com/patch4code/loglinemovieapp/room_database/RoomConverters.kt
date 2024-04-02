package com.patch4code.loglinemovieapp.room_database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.time.LocalDateTime
import java.time.ZoneOffset

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

    @TypeConverter
    fun fromMovieList(movieList: List<Movie>?): String? {
        return Gson().toJson(movieList)
    }
    @TypeConverter
    fun toMovieList(movieListString: String?): List<Movie>? {
        val type = object : TypeToken<List<Movie>?>() {}.type
        return Gson().fromJson(movieListString, type)
    }

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }
    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }
}