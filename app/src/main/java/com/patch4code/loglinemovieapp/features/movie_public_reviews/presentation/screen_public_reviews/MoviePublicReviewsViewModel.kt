package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.TmdbReview
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MoviePublicReviewsViewModel - ViewModel responsible for managing public reviews for a movie.
 *
 * @author Patch4Code
 */
class MoviePublicReviewsViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _tmdbMovieReviews = MutableLiveData<List<TmdbReview>>()
    val tmdbMovieReviews: LiveData<List<TmdbReview>> get() = _tmdbMovieReviews
    private val _tmdbIsLoading = MutableLiveData<Boolean>()
    val tmdbIsLoading: LiveData<Boolean> get() = _tmdbIsLoading
    private val _hasTmdbLoadError = MutableLiveData<Boolean>()
    val hasTmdbLoadError: LiveData<Boolean> get() = _hasTmdbLoadError

    private var highestLoadedPage = 1
    private var pageAmount = 1
    private var currentMovieId = -1


    // Loads TMDB reviews for the given movieId
    fun loadTmdbReviews(movieId: Int){
        currentMovieId = movieId

        viewModelScope.launch {
            _tmdbIsLoading.value = true
            _hasTmdbLoadError.value = false
            try {
                val reviewsResponse = tmdbApiService.getTmdbMovieReviews(movieId)
                if(reviewsResponse.isSuccessful){
                    pageAmount = reviewsResponse.body()?.totalPages!!
                    _tmdbMovieReviews.value = reviewsResponse.body()?.results

                    _tmdbIsLoading.value = false
                }
            } catch (e: Exception) {
                _tmdbIsLoading.value = false
                _hasTmdbLoadError.value = true
                Log.e("MoviePublicReviewsViewModel", "Error getting tmdb movie reviews", e)
            }
            finally {
                highestLoadedPage = 1
            }
        }
    }

    // Loads more TMDB reviews for the current movie (next page)
    fun loadMoreTmdbReviews(){
        //Log.e("MoviePublicReviewsViewModel", "highestLoadedPage: $highestLoadedPage, pageAmount: $pageAmount")
        if(highestLoadedPage < pageAmount){
            highestLoadedPage++
            val currentReviews = _tmdbMovieReviews.value?.toMutableList() ?: mutableListOf()

            viewModelScope.launch {
                try {
                    val loadMoreReviewsResponse = tmdbApiService.getTmdbMovieReviews(movieId = currentMovieId, page = highestLoadedPage)

                    if(loadMoreReviewsResponse.isSuccessful){
                        val newLoadedReviews = loadMoreReviewsResponse.body()?.results
                        //Log.e("MoviePublicReviewsViewModel", "newLoadedReviews: $newLoadedReviews")

                        if (newLoadedReviews != null) {
                            currentReviews.addAll(newLoadedReviews)
                        }
                        _tmdbMovieReviews.value = currentReviews
                    }
                } catch (e: Exception) {
                    Log.e("MoviePublicReviewsViewModel", "Error loading more tmdb movie reviews", e)
                }
            }
        }
    }
}