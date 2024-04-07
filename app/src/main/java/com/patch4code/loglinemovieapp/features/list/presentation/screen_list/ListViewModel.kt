package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.room_database.MovieListDao
import kotlinx.coroutines.launch

class ListViewModel(private val movieListDao: MovieListDao): ViewModel() {

    private val _movieList = MutableLiveData<MovieList>()
    val movieList: LiveData<MovieList> get() = _movieList

    fun setList(movieList: MovieList) {
        viewModelScope.launch {
            _movieList.value = movieListDao.getMovieListById(movieList.id)
        }
    }

    fun addMovieToList(movie: Movie) {
        val listId = _movieList.value?.id
        viewModelScope.launch {
            listId?.let { movieListDao.addMovieToList(it, movie) }
            _movieList.value = listId?.let { movieListDao.getMovieListById(it) }
        }
    }

    fun isMovieAlreadyOnList(movie: Movie): Boolean{
        val movieList = _movieList.value?.movies
        return movieList?.contains(movie) ?: false
    }

    fun removeMovieFromList(movieId: Int) {
        val listId = _movieList.value?.id
        viewModelScope.launch {
            listId?.let { movieListDao.removeMovieFromList(it, movieId) }
            _movieList.value = listId?.let { movieListDao.getMovieListById(it) }
        }
    }

    fun editList(newTitle: String, newIsPublicState: Boolean){
        val listId = _movieList.value?.id
        viewModelScope.launch {
            listId?.let { movieListDao.editListParameters(it, newTitle, newIsPublicState) }
            _movieList.value = listId?.let { movieListDao.getMovieListById(it) }
        }
    }

    fun deleteList(){
        val listId = _movieList.value?.id
        viewModelScope.launch {
            listId?.let { movieListDao.deleteMovieListById(it) }
        }
    }
}


class ListViewModelFactory(private val movieListDao: MovieListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(movieListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}