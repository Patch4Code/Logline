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

                var authorName = "Anonymous"
                var avatarPath = ""

                val currentUserQuery = ParseQuery.getQuery<ParseObject>("UserProfile")
                currentUserQuery.whereEqualTo("user", ParseObject.createWithoutData("_User", userId))
                currentUserQuery.getFirstInBackground{ currentProfile, e ->
                    if (e!=null){
                        Log.e("PublicProfileReviewsViewModel", "Error loading currentProfile: ", e)
                    }else{
                        authorName = currentProfile.getString("userName") ?: "Anonymous"
                        avatarPath = currentProfile.getParseFile("profileImage")?.url ?: ""
                    }
                }

                val loggedMoviesQuery = ParseQuery.getQuery<ParseObject>("LoggedMovie")
                loggedMoviesQuery.whereEqualTo("user", ParseObject.createWithoutData("_User", userId))
                loggedMoviesQuery.findInBackground{ userReviews, e ->
                    if (e!=null){
                        Log.e("PublicProfileReviewsViewModel", "Error loading reviews: ", e)
                    }else{
                        val publicCurrentUserReviews = mutableListOf<LoglineReview>()
                        for (review in userReviews){
                            val objectId = review.objectId
                            val movieJson = review.getString("movie")
                            val type: Type = object : TypeToken<Movie>() {}.type
                            val movie: Movie = JSONHelper.fromJsonWithType(movieJson, type)
                            val content = review.getString("review") ?: ""
                            val longDate = review.getNumber("date")?.toLong()
                            val date = DateHelper.convertLongToLocalDateTime(longDate)
                            val rating = review.getNumber("rating")?.toInt() ?: 0

                            val userReview = LoglineReview(
                                objectId = objectId,
                                authorName = authorName,
                                userId = userId,
                                content = content,
                                movie = movie,
                                createdAt = date,
                                avatarPath = avatarPath,
                                rating = rating
                            )
                            publicCurrentUserReviews.add(userReview)
                        }
                        _publicProfileReviews.value = publicCurrentUserReviews
                    }
                }
            } catch (e: Exception) {
                Log.e("PublicProfileReviewsViewModel", "Catch Error: ", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}