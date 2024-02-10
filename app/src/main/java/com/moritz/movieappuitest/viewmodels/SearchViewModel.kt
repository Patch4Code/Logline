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

class SearchViewModel: ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _searchedMovies = MutableLiveData<List<Movie>>()
    val searchedMovies: LiveData<List<Movie>>
        get() = _searchedMovies

    private var highestLoadedPage = 1
    private var pageAmount = 1
    private var currentSearchQuery = MutableLiveData<String>()

    fun searchMovie(searchQuery: String){
        currentSearchQuery.value = searchQuery

        viewModelScope.launch {
            try {
                val searchResponse = tmdbApiService.searchMovie(searchQuery = searchQuery)
                if(searchResponse.isSuccessful){
                    pageAmount = searchResponse.body()?.totalPages!!
                    _searchedMovies.value = searchResponse.body()?.results
                    //Log.e("SearchViewModel - Search Sucessfull", _searchedMovies.value.toString())
                    //Log.e("SearchViewModel - page number", " Pagenumber: $pageNumber")
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error searching movies", e)
            }
            finally {
                highestLoadedPage = 1
            }
        }
    }

    fun loadMoreMovies(){
        Log.e("PageNumer index", "CureentPage: $highestLoadedPage, MaxPage: $pageAmount")
        if(highestLoadedPage <= pageAmount){
            highestLoadedPage++
            //Log.e("SearchViewModel - loadMoreMovies " ,"if erfüllt")
            val currentMovies = _searchedMovies.value?.toMutableList() ?: mutableListOf()
            //Log.e("SearchViewModel - currentMovies" , "Current Movies: $currentMovies")
            //Log.e("SearchViewModel - load more currentSearchQuery", "${currentSearchQuery.value}")

            viewModelScope.launch {
                try {
                    val loadMoreResponse = tmdbApiService.searchMovie(searchQuery = currentSearchQuery.value, page = highestLoadedPage)

                    //Log.e("SearchViewModel - loadMoreResponse", loadMoreResponse.body().toString())
                    if(loadMoreResponse.isSuccessful){
                        //Log.e("SearchViewModel - loadMoreMovies" ,"is sucessfull")
                        val newLoadedMovies = loadMoreResponse.body()?.results
                        if (newLoadedMovies != null) {
                            currentMovies.addAll(newLoadedMovies)
                            //Log.e("SearchViewModel - loadMoreMovies movies added" , "Current Movies: $currentMovies")
                        }
                        //_searchedMovies.value = searchResponse.body()
                        //Log.e("Search Sucessfull", _searchedMovies.value.toString())

                        _searchedMovies.value = currentMovies
                    }
                } catch (e: Exception) {
                    Log.e("SearchViewModel", "Error loading more movies", e)
                }
            }
        }
    }
}