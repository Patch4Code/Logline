package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.domain.model.userDataList
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.Cast
import com.patch4code.loglinemovieapp.features.movie.domain.model.Crew
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel(){

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
                    //bring the strings of each cast-character and also crew-job to the same length  for better displaying

                    val equalizedCast = movieCreditsResponse.body()?.let { equalizeCastCharacterStringLength(it.cast) }
                    val equalizedCrew =  movieCreditsResponse.body()?.let{ equalizeCrewJobStringLength(it.crew)}

                    //Log.e("MovieViewModel","equalizedCrew: $equalizedCrew")
                    val equalizedCredits = MovieCredits(
                        id = movieCreditsResponse.body()?.id ?: -1,
                        cast = equalizedCast ?: emptyList(),
                        crew = equalizedCrew ?: emptyList()
                    )

                    _creditsData.value = movieCreditsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie credits", e)
            }
        }
    }

    private fun equalizeCastCharacterStringLength(castMembers: List<Cast>): List<Cast> {
        val maxStringLength = castMembers.maxOfOrNull { it.character.length } ?: 0

        // Gleichmachen der Längen der Characters
        return castMembers.map { cast ->
            val padding = " ".repeat(maxStringLength - cast.character.length)
            val updatedCharacter = cast.character + padding
            val updatedProfilePath = if(cast.profilePath != null) cast.profilePath else ""
            cast.copy(character = updatedCharacter, profilePath = updatedProfilePath)
        }
    }
    private fun equalizeCrewJobStringLength(crewMembers: List<Crew>): List<Crew> {
        val maxStringLength = crewMembers.maxOfOrNull { it.job.length } ?: 0

        return crewMembers.map { crew ->
            val padding = " ".repeat(maxStringLength - crew.job.length)
            val updatedCharacter = crew.job + padding
            val updatedProfilePath = if(crew.profilePath != null) crew.profilePath else ""
            crew.copy(job = updatedCharacter, profilePath = updatedProfilePath)
        }
    }




    fun loadMovieCollection(collectionId: Int){
        //Log.e("MovieViewModel","collectionId: $collectionId")
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
        val movieUserData = userDataList.find { (it.movie?.id ?: -1) == id }
        if (movieUserData != null){
            _myRating.value = movieUserData.rating
            _onWatchlist.value = movieUserData.onWatchlist
        }else{
            _myRating.value = -1
            _onWatchlist.value = false
        }
    }

    fun changeRating(id: Int?, rating: Int){
        val movieUserData = userDataList.find { it.movie?.id == id }
        if (movieUserData != null){
            movieUserData.rating = rating
        }else{
            val newMovieUserData = MovieUserData(
                movie = Movie(
                    title = _detailsData.value?.title ?: "N/A",
                    id = id ?: -1,
                    releaseDate = _detailsData.value?.releaseDate ?: "N/A",
                    posterUrl = _detailsData.value?.posterPath ?: ""
                ),
                onWatchlist = false,
                rating = rating
            )
            userDataList.add(newMovieUserData)
        }
        _myRating.value = rating
    }

    fun changeOnWatchlist(id: Int?, newOnWatchlistState: Boolean){
        val movieUserData = userDataList.find { it.movie?.id == id }
        if (movieUserData != null){
            movieUserData.onWatchlist = newOnWatchlistState
        }else{
            val newMovieUserData = MovieUserData(
                movie = Movie(
                    title = _detailsData.value?.title ?: "N/A",
                    id = id ?: -1,
                    releaseDate = _detailsData.value?.releaseDate ?: "N/A",
                    posterUrl = _detailsData.value?.posterPath ?: ""
                ),
                onWatchlist = newOnWatchlistState,
            )
            userDataList.add(newMovieUserData)
        }
        _onWatchlist.value = newOnWatchlistState
    }
}