package com.moritz.movieappuitest.features.list.presentation.screen_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moritz.movieappuitest.api.RetrofitHelper
import com.moritz.movieappuitest.api.TmdbApiService
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.core.presentation.utils.TmdbCredentials
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.domain.model.userMovieListsDummy
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {

    private val _movieList = MutableLiveData<MovieList>()
    val movieList: LiveData<MovieList> get() = _movieList

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }
    private val _searchedMovies = MutableLiveData<List<Movie>>()
    val searchedMovies: LiveData<List<Movie>>
        get() = _searchedMovies


    fun setList(movieList: MovieList) {
        //set according to Dummy

        val foundMovieList = userMovieListsDummy.find { it.name == movieList.name }
        _movieList.value = foundMovieList ?: movieList
    }


    private fun updateList(movieList: MovieList) {
        _movieList.value = movieList
    }

    fun addMovieToList(movie: Movie) {
        val updatedMovies = _movieList.value?.movies.orEmpty().toMutableList()
        val listName = _movieList.value?.name
        updatedMovies.add(movie)

        // Change Dummy-Data and accordingly the local ViewModel Data
        userMovieListsDummy.find { it.name == listName }?.movies = updatedMovies
        userMovieListsDummy.find { it.name == listName }?.let { updateList(it) }

        //Log.e("ListViewModel","_movieList.value: ${_movieList.value}")
    }

    fun isMovieAlreadyOnList(movie: Movie): Boolean{
        val movieList = _movieList.value?.movies
        return movieList?.contains(movie) ?: false
    }


    fun removeMovieFromList(movieId: Int) {
        val updatedMovies = _movieList.value?.movies.orEmpty().toMutableList()
        val listName = _movieList.value?.name
        updatedMovies.removeIf {it.id == movieId}

        // Change Dummy-Data and accordingly the local ViewModel Data
        userMovieListsDummy.find { it.name == listName }?.movies = updatedMovies
        userMovieListsDummy.find { it.name == listName }?.let { updateList(it) }
    }


    fun searchMovie(searchQuery: String){
        viewModelScope.launch {
            try {
                val searchResponse = tmdbApiService.searchMovie(searchQuery = searchQuery)
                if(searchResponse.isSuccessful){
                    _searchedMovies.value = searchResponse.body()?.results
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error searching movies", e)
            }
        }
    }

    fun editList(newTitle: String, newIsPublicState: Boolean){
        val listName = _movieList.value?.name

        userMovieListsDummy.find { it.name == listName }?.isPublic = newIsPublicState
        userMovieListsDummy.find { it.name == listName }?.name = newTitle

        userMovieListsDummy.find { it.name == newTitle }?.let { updateList(it) }
    }

    fun deleteList(){
        userMovieListsDummy.remove(_movieList.value)
    }

    fun isListNameUnique(newName: String): Boolean{
        return !userMovieListsDummy.any { it.name == newName }
    }
}
