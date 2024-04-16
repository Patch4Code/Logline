package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.parse.ParseObject
import com.parse.ParseQuery
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.LoglineReview
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PublicReviewsViewModel: ViewModel() {

    private val _publicReviews = MutableLiveData<List<LoglineReview>>()
    val publicReviews: LiveData<List<LoglineReview>> get() = _publicReviews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPublicReviews() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Kotlin suspendCoroutine waits for the completion of the background operation
                val publicReviews = suspendCoroutine<List<ParseObject>> { continuation ->
                    val reviewsQuery = ParseQuery<ParseObject>("LoggedMovie")
                    // Execute query in background
                    reviewsQuery.findInBackground { publicReviews, e ->
                        if (e != null) {
                            Log.e("PublicReviewsViewModel", "Error: ", e)
                            continuation.resumeWithException(e)
                        } else {
                            continuation.resume(publicReviews)
                        }
                    }
                }
                // use the received publicReviews to create List<LoglineReview>
                val publicLoglineReviews = buildPublicLoglineReviews(publicReviews)

                // Set the value of publicReviews LiveData to the fetched List<LoglineReview> sorted by date
                _publicReviews.value = publicLoglineReviews.sortedByDescending { it.createdAt }
            } catch (e: Exception) {
                Log.e("PublicReviewsViewModel", "Error: ", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Creates List<LoglineReview> from given List<ParseObject>
    private suspend fun buildPublicLoglineReviews(publicReviews: List<ParseObject>): List<LoglineReview> {
        return suspendCoroutine { continuation ->
            val publicLoglineReviews = mutableListOf<LoglineReview>()
            val countDownLatch = CountDownLatch(publicReviews.size)

            publicReviews.forEach { log ->
                val objectId = log.objectId
                val content = log.getString("review") ?: ""
                val longDate = log.getNumber("date")?.toLong()
                val date = DateHelper.convertLongToLocalDateTime(longDate)
                val rating = log.getNumber("rating")?.toInt() ?: 0
                val user = log.getParseUser("user")
                val userId = user?.objectId ?: ""
                val movieJson = log.getString("movie")
                val type: Type = object : TypeToken<Movie>() {}.type
                val movie: Movie = JSONHelper.fromJsonWithType(movieJson, type)

                var avatarPath = ""
                var authorName = "Anonymous"

                val profileQuery = ParseQuery.getQuery<ParseObject>("UserProfile")
                profileQuery.whereEqualTo("isPublic", true)
                profileQuery.whereEqualTo("user", user)
                profileQuery.findInBackground { profiles, e ->
                    if (e != null) {
                        Log.e("PublicReviewsViewModel", "Error loading user info: ", e)
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
                        movie = movie,
                        createdAt = date,
                        avatarPath = avatarPath,
                        rating = rating
                    )
                    publicLoglineReviews.add(review)

                    countDownLatch.countDown()
                    if (countDownLatch.count == 0L) {
                        continuation.resume(publicLoglineReviews)
                    }
                }
            }
        }
    }
}