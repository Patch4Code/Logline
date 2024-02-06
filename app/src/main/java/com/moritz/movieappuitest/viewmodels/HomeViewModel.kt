package com.moritz.movieappuitest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moritz.movieappuitest.api.tmdb.RetrofitHelper
import com.moritz.movieappuitest.api.tmdb.TmdbApiService
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.TmdbCredentials
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    fun loadPopularMovies(){
       viewModelScope.launch {
           try {
               val response = tmdbApiService.getPopularMovies()//.body()?.results
               if(response.isSuccessful){
                   _popularMovies.value = response.body()?.results
               }
           }
           catch (e: Exception) {
               Log.e("HomeViewModel", "Error loading popular movies", e)
           }
       }

    }

}


