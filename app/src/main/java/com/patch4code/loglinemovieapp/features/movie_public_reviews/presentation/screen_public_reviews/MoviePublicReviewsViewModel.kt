package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.Review
import kotlinx.coroutines.launch

class MoviePublicReviewsViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _tmdbMovieReviews = MutableLiveData<List<Review>>()
    val tmdbMovieReviews: LiveData<List<Review>> get() = _tmdbMovieReviews

    private var highestLoadedPage = 1
    private var pageAmount = 1
    private var currentMovieId = -1

    fun loadTmdbReviews(movieId: Int){
        currentMovieId = movieId

        viewModelScope.launch {
            try {
                val reviewsResponse = tmdbApiService.getTmdbMovieReviews(movieId)
                if(reviewsResponse.isSuccessful){
                    pageAmount = reviewsResponse.body()?.totalPages!!
                    _tmdbMovieReviews.value = reviewsResponse.body()?.results
                }
            } catch (e: Exception) {
                Log.e("MoviePublicReviewsViewModel", "Error getting tmdb movie reviews", e)
            }
            finally {
                highestLoadedPage = 1
            }
        }
    }


    fun loadMoreTmdbReviews(){

        Log.e("MoviePublicReviewsViewModel", "highestLoadedPage: $highestLoadedPage, pageAmount: $pageAmount")
        if(highestLoadedPage < pageAmount){
            highestLoadedPage++
            val currentReviews = _tmdbMovieReviews.value?.toMutableList() ?: mutableListOf()

            viewModelScope.launch {
                try {
                    val loadMoreReviewsResponse = tmdbApiService.getTmdbMovieReviews(movieId = currentMovieId, page = highestLoadedPage)

                    if(loadMoreReviewsResponse.isSuccessful){
                        val newLoadedReviews = loadMoreReviewsResponse.body()?.results
                        Log.e("MoviePublicReviewsViewModel", "newLoadedReviews: $newLoadedReviews")

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