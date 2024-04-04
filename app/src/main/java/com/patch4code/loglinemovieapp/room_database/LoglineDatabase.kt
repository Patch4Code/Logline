package com.patch4code.loglinemovieapp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Database(entities = [MovieUserData::class, LoggedMovie::class, MovieList::class, UserProfile::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class LoglineDatabase: RoomDatabase() {

    abstract val movieUserDataDao: MovieUserDataDao
    abstract val loggedMovieDao: LoggedMovieDao
    abstract val movieListDao: MovieListDao
    abstract val userProfileDao: UserProfileDao
}