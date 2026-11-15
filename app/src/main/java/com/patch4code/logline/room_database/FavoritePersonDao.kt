package com.patch4code.logline.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patch4code.logline.features.person_details.domain.model.FavoritePerson
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePersonDao {
    @Query("SELECT * FROM favorite_persons")
    fun getAllFavoritePersons(): Flow<List<FavoritePerson>>

    @Query("SELECT * FROM favorite_persons WHERE id = :personId")
    suspend fun getFavoritePersonById(personId: Int): FavoritePerson?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePerson(person: FavoritePerson)

    @Delete
    suspend fun deleteFavoritePerson(person: FavoritePerson)

    @Query("DELETE FROM favorite_persons WHERE id = :personId")
    suspend fun deleteFavoritePersonById(personId: Int)
}
