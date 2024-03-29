package com.patch4code.loglinemovieapp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData

@Database(entities = [MovieUserData::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class LoglineDatabase: RoomDatabase() {

    abstract val dao: MovieUserDataDao
}