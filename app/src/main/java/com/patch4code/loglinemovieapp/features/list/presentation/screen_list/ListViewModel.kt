package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
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

    fun makeListPublic(onSuccess:(publishStatus: String)->Unit, onError:(exception: Exception)->Unit){
        viewModelScope.launch {
            val publishStatus: String
            try {
                val user = ParseUser.getCurrentUser()

                val query = ParseQuery<ParseObject>("MovieList")
                query.whereEqualTo("user", ParseUser.getCurrentUser())
                _movieList.value?.id?.let { query.whereEqualTo("listId", it) }
                val existingMovieList = query.find().firstOrNull()
                Log.e("ListViewModel", "existingMovieList: $existingMovieList")

                val movieList: ParseObject
                if(existingMovieList != null){
                    publishStatus = "Updated"
                    movieList = existingMovieList
                    Log.e("ListViewModel", "Update")
                }else{
                    publishStatus = "Newly Published"
                    movieList = ParseObject("MovieList")
                    Log.e("ListViewModel", "create")
                }

                movieList.put("user", user)
                _movieList.value?.name?.let { movieList.put("name", it) }
                _movieList.value?.id?.let { movieList.put("listId", it) }
                _movieList.value?.isRanked?.let { movieList.put("isRanked", it) }
                val moviesJson = _movieList.value?.movies?.toJson() ?: "[]"
                movieList.put("moviesString", moviesJson)

                val acl = ParseACL()
                acl.setWriteAccess(ParseUser.getCurrentUser(), true)
                acl.publicReadAccess = true
                movieList.acl = acl

                movieList.saveInBackground{exception->
                    if(exception == null){
                        onSuccess(publishStatus)
                        //Log.e("ListViewModel", "Success")
                    }else{
                        onError(exception)
                        Log.e("ListViewModel", "Error: ", exception)
                    }
                }


            }catch (e: Exception){
                Log.e("ListViewModel", "Catch Error: ", e)
                onError(e)
            }
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