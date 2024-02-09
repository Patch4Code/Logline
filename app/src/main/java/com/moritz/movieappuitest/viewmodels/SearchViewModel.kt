package com.moritz.movieappuitest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moritz.movieappuitest.api.tmdb.RetrofitHelper
import com.moritz.movieappuitest.api.tmdb.TmdbApiService
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.utils.TmdbCredentials
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _searchedMovies = MutableLiveData<MovieList>()
    val searchedMovies: LiveData<MovieList>
        get() = _searchedMovies

    fun searchMovie(searchQuery: String){
        viewModelScope.launch {
            try {
                Log.e("Search Word", searchQuery)

                val searchResponse = tmdbApiService.searchMovie(searchQuery = searchQuery)
                if(searchResponse.isSuccessful){
                    _searchedMovies.value = searchResponse.body()
                    Log.e("Search Sucessfull", _searchedMovies.value.toString())
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error searching movies", e)
            }
        }
    }
}