package com.patch4code.loglinemovieapp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoglineDatabase - Database class for managing the SQLite database using Room.
 * This class defines the entities and version of the database, as well as provides access to DAOs.
 *
 * @author Patch4Code
 */
@Database(entities = [MovieUserData::class, LoggedMovie::class, MovieList::class, UserProfile::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class LoglineDatabase: RoomDatabase() {

    abstract val movieUserDataDao: MovieUserDataDao
    abstract val loggedMovieDao: LoggedMovieDao
    abstract val movieListDao: MovieListDao
    abstract val userProfileDao: UserProfileDao
}