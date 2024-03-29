package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.room_database.MovieUserDataDao
import kotlinx.coroutines.launch

class MyMoviesViewModel(private val dao: MovieUserDataDao): ViewModel() {

    private val _myUserDataList = MutableLiveData<List<MovieUserData>>()
    val myUserDataList: LiveData<List<MovieUserData>> get() = _myUserDataList

    init {
        setUserdataList()
    }

    private fun setUserdataList() {
        viewModelScope.launch {
            _myUserDataList.value = dao.getMovieUserDataList()
        }

        //_myUserDataList.value = userDataList
    }
}

class MyMoviesViewModelFactory(private val dao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyMoviesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}