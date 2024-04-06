package com.patch4code.loglinemovieapp.features.core.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import kotlinx.coroutines.launch

class GeneralMovieSearchViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }
    private val _searchedMovies = MutableLiveData<List<Movie>>()
    val searchedMovies: LiveData<List<Movie>>
        get() = _searchedMovies

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
}