package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

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

class PublicProfileReviewsViewModel: ViewModel() {

    private val _publicProfileReviews = MutableLiveData<List<LoglineReview>>()
    val publicProfileReviews: LiveData<List<LoglineReview>> get() = _publicProfileReviews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPublicProfileReviews(userId: String?) {
        viewModelScope.launch {
            try {
                if (userId == null) return@launch
                _isLoading.value = true

                // Kotlin suspendCoroutine waits for the completion of the background operation
                val publicReviewsParseObject = suspendCoroutine<List<ParseObject>> { continuation ->
                    val profileReviewsQuery = ParseQuery<ParseObject>("LoggedMovie")
                    profileReviewsQuery.whereEqualTo("user", ParseObject.createWithoutData("_User", userId))
                    // Execute query in background
                    profileReviewsQuery.findInBackground { publicReviews, e ->
                        if (e != null) {
                            Log.e("PublicProfileReviewsViewModel", "Error: ", e)
                            continuation.resumeWithException(e)
                        } else {
                            continuation.resume(publicReviews)
                        }
                    }
                }

                if (publicReviewsParseObject.isEmpty()){
                    _publicProfileReviews.value = emptyList()
                }else{
                    val profileLoglineReviews = buildProfileLoglineReviews(publicReviewsParseObject)
                    _publicProfileReviews.value = profileLoglineReviews
                }
            } catch (e: Exception) {
                Log.e("PublicProfileReviewsViewModel", "Catch Error: ", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Creates List<LoglineReview> from given List<ParseObject>
    private suspend fun buildProfileLoglineReviews(profileReviews: List<ParseObject>): List<LoglineReview> {
        return suspendCoroutine { continuation ->
            val publicCurrentUserReviews  = mutableListOf<LoglineReview>()
            val countDownLatch = CountDownLatch(profileReviews.size)

            profileReviews.forEach { log ->
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
                profileQuery.whereEqualTo("user", user)
                profileQuery.findInBackground { profiles, e ->
                    if (e != null) {
                        Log.e("PublicProfileReviewsViewModel", "Error loading user info: ", e)
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
                    publicCurrentUserReviews.add(review)

                    countDownLatch.countDown()
                    if (countDownLatch.count == 0L) {
                        continuation.resume(publicCurrentUserReviews)
                    }
                }
            }
        }
    }
}