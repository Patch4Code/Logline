package com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.room_database.MovieUserDataDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * WatchlistViewModel - ViewModel responsible for for managing movie user data of movies on watchlist.
 *
 * @param dao MovieUserDataDao instance for accessing movie user data from the db.
 * @author Patch4Code
 */
class WatchlistViewModel(private val dao: MovieUserDataDao): ViewModel() {

    private val _myUserDataList = MutableLiveData<List<MovieUserData>>()
    val myUserDataList: LiveData<List<MovieUserData>> get() = _myUserDataList

    fun setUserdataList() {
        viewModelScope.launch {
            _myUserDataList.value = dao.getMovieUserDataList()
        }
    }
}

// Factory-class for creating WatchlistViewModel instances to manage access to the database
class WatchlistViewModelFactory(private val dao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WatchlistViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}