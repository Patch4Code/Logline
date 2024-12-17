package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.search.domain.model.SearchHistoryItem

@Dao
interface SearchHistoryDao {

    @Upsert
    suspend fun upsertSearchHistoryItem(searchString: SearchHistoryItem)

    @Delete
    suspend fun deleteSearchHistoryItem(searchHistoryItem: SearchHistoryItem)

    @Query("SELECT * From SearchHistoryItem ORDER BY addTime DESC")
    suspend fun getSearchHistory(): List<SearchHistoryItem>

    @Transaction
    suspend fun addSearchString(searchString: String){
        upsertSearchHistoryItem(SearchHistoryItem(searchString))
    }

    @Query("DELETE FROM SearchHistoryItem")
    suspend fun clearSearchHistory()


}