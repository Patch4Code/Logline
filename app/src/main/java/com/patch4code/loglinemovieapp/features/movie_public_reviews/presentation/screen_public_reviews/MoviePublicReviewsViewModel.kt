package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseObject
import com.parse.ParseQuery
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.TmdbReview
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MoviePublicReviewsViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _tmdbMovieReviews = MutableLiveData<List<TmdbReview>>()
    val tmdbMovieReviews: LiveData<List<TmdbReview>> get() = _tmdbMovieReviews
    private val _tmdbIsLoading = MutableLiveData<Boolean>()
    val tmdbIsLoading: LiveData<Boolean> get() = _tmdbIsLoading


    private var highestLoadedPage = 1
    private var pageAmount = 1
    private var currentMovieId = -1

    private val _loglineMovieReviews = MutableLiveData<List<LoglineReview>>()
    val loglineMovieReviews: LiveData<List<LoglineReview>> get() = _loglineMovieReviews
    private val _loglineIsLoading = MutableLiveData<Boolean>()
    val loglineIsLoading: LiveData<Boolean> get() = _loglineIsLoading

    fun loadTmdbReviews(movieId: Int){
        currentMovieId = movieId

        viewModelScope.launch {
            _tmdbIsLoading.value = true
            try {
                val reviewsResponse = tmdbApiService.getTmdbMovieReviews(movieId)
                if(reviewsResponse.isSuccessful){
                    pageAmount = reviewsResponse.body()?.totalPages!!
                    _tmdbMovieReviews.value = reviewsResponse.body()?.results

                    _tmdbIsLoading.value = false
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



    fun loadLoglineReviews(movieId: Int){
        viewModelScope.launch{
            try {
                _loglineIsLoading.value = true

                val movieReviews = suspendCoroutine<List<ParseObject>> {continuation ->
                    val reviewQuery = ParseQuery<ParseObject>("LoggedMovie")
                    reviewQuery.whereEqualTo("movieId", movieId)
                    reviewQuery.findInBackground { foundReviewLogs, e ->
                        if (e!=null){
                            Log.e("MoviePublicReviewsViewModel", "Error: ", e)
                            continuation.resumeWithException(e)
                        }else{
                            continuation.resume(foundReviewLogs)
                        }
                    }
                }
                val loglineMovieReviews = buildLoglineMovieReviews(movieReviews)

                _loglineMovieReviews.value = loglineMovieReviews
            }catch (e: Exception){
                Log.e("MoviePublicReviewsViewModel", "Catch Error: ", e)
            }finally {
                _loglineIsLoading.value = false
            }
        }
    }

    private suspend fun buildLoglineMovieReviews(movieReviews:List<ParseObject>): List<LoglineReview>{
        return suspendCoroutine { continuation ->
            val publicMovieLoglineReviews = mutableListOf<LoglineReview>()
            val countDownLatch = CountDownLatch(movieReviews.size)

            movieReviews.forEach { log->
                val objectId = log.objectId
                val content = log.getString("review") ?: ""
                val date = DateHelper.convertLongToLocalDateTime(log.getNumber("date")?.toLong())
                val rating = log.getNumber("rating")?.toInt() ?: 0
                val user = log.getParseUser("user")
                val userId = user?.objectId ?: ""

                var avatarPath = ""
                var authorName = "Anonymous"

                val profileQuery = ParseQuery.getQuery<ParseObject>("UserProfile")
                profileQuery.whereEqualTo("isPublic", true)
                profileQuery.whereEqualTo("user", user)
                profileQuery.findInBackground { profiles, e ->
                    if (e != null) {
                        Log.e("MoviePublicReviewsViewModel", "Error loading user info: ", e)
                    } else {
                        val profile = profiles.firstOrNull()
                        if (profile != null) {
                            avatarPath = profile.getParseFile("profileImage")?.url ?: ""
                            authorName = profile.getString("userName") ?: "Anonymous"
                        }
                    }
                    val review = LoglineReview(
                        objectId = objectId,
                        authorName = authorName,
                        userId = userId,
                        content = content,
                        createdAt = date,
                        avatarPath = avatarPath,
                        rating = rating
                    )
                    publicMovieLoglineReviews.add(review)

                    countDownLatch.countDown()
                    if (countDownLatch.count == 0L){
                        continuation.resume(publicMovieLoglineReviews)
                    }
                }
            }
        }
    }
}