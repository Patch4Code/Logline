package com.patch4code.logline.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.domain.model.MovieUserData
import com.patch4code.logline.features.diary.domain.model.LoggedMovie
import com.patch4code.logline.features.list.domain.model.MovieInList
import com.patch4code.logline.features.list.domain.model.MovieList
import com.patch4code.logline.features.profile.domain.model.UserProfile
import com.patch4code.logline.features.search.domain.model.SearchHistoryItem

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoglineDatabase - Database class for managing the SQLite database using Room.
 * This class defines the entities and version of the database, as well as provides access to DAOs.
 *
 * @author Patch4Code
 */
@Database(
    entities = [
        Movie::class,
        MovieUserData::class,
        LoggedMovie::class,
        MovieList::class,
        MovieInList::class,
        UserProfile::class,
        SearchHistoryItem::class],
    version = 1)
@TypeConverters(RoomConverters::class)
abstract class LoglineDatabase: RoomDatabase() {

    abstract val movieDao: MovieDao
    abstract val movieUserDataDao: MovieUserDataDao
    abstract val loggedMovieDao: LoggedMovieDao
    abstract val movieListDao: MovieListDao
    abstract val movieInListDao: MovieInListDao
    abstract val userProfileDao: UserProfileDao
    abstract val searchHistoryDao: SearchHistoryDao
}