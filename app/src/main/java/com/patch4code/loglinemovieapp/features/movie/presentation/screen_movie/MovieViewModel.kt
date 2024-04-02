package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.room_database.MovieUserDataDao
import kotlinx.coroutines.launch

class MovieViewModel(private val dao: MovieUserDataDao): ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _detailsData = MutableLiveData<MovieDetails>()
    val detailsData: LiveData<MovieDetails> get() = _detailsData

    private val _creditsData = MutableLiveData<MovieCredits>()
    val creditsData: LiveData<MovieCredits> get() = _creditsData

    private val _collectionMovies = MutableLiveData<List<Movie>>()
    val collectionMovies: LiveData<List<Movie>> get() = _collectionMovies

    private val _myRating = MutableLiveData<Int>()
    val myRating: LiveData<Int> get() = _myRating

    private val _onWatchlist = MutableLiveData<Boolean>()
    val onWatchlist: LiveData<Boolean> get() = _onWatchlist


    fun loadMovieDetails(movieId: Int){
        viewModelScope.launch {
            try {
                val movieDetailsResponse = tmdbApiService.getMovieDetails(movieId = movieId)
                if(movieDetailsResponse.isSuccessful){
                    _detailsData.value = movieDetailsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie details", e)
            }
        }
    }

    fun loadMovieCredits(movieId: Int){
        viewModelScope.launch {
            try {
                val movieCreditsResponse = tmdbApiService.getMovieCredits(movieId = movieId)
                if(movieCreditsResponse.isSuccessful){

                    _creditsData.value = movieCreditsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie credits", e)
            }
        }
    }


    fun loadMovieCollection(collectionId: Int){
        viewModelScope.launch {
            try {
                val movieCollectionResponse = tmdbApiService.getMoviesFromCollection(collectionId = collectionId)
                if(movieCollectionResponse.isSuccessful){
                    _collectionMovies.value = movieCollectionResponse.body()?.movies
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie collection", e)
            }
        }
    }

    fun loadRatingAndWatchlistStatusById(id: Int){
        var movieUserData: MovieUserData? = null
        viewModelScope.launch {
            movieUserData = dao.getMovieUserDataByMovieId(id)

            Log.e("MovieViewModel", "movieUserData: $movieUserData")

            if (movieUserData != null){
                _myRating.value = movieUserData!!.rating
                _onWatchlist.value = movieUserData!!.onWatchlist
            }else{
                _myRating.value = -1
                _onWatchlist.value = false
            }
        }
    }

    fun changeRating(id: Int?, rating: Int){
        val movie = Movie(
            title = _detailsData.value?.title ?: "N/A",
            id = id ?: -1,
            releaseDate = _detailsData.value?.releaseDate ?: "N/A",
            posterUrl = _detailsData.value?.posterPath ?: ""
        )

        viewModelScope.launch {
            dao.updateOrInsertRating(movie, rating)
        }
        _myRating.value = rating
    }

    fun changeOnWatchlist(id: Int?, newOnWatchlistState: Boolean){
        val movie = Movie(
            title = _detailsData.value?.title ?: "N/A",
            id = id ?: -1,
            releaseDate = _detailsData.value?.releaseDate ?: "N/A",
            posterUrl = _detailsData.value?.posterPath ?: ""
        )

        viewModelScope.launch {
            dao.updateOrInsertOnWatchlist(movie, newOnWatchlistState)
        }
        _onWatchlist.value = newOnWatchlistState
    }
}

class MovieViewModelFactory(private val dao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}