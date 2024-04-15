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

class PublicReviewsViewModel: ViewModel() {

    private val _publicReviews = MutableLiveData<List<LoglineReview>>()
    val publicReviews: LiveData<List<LoglineReview>> get() = _publicReviews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPublicReviews(){
        viewModelScope.launch {

            try {
                _isLoading.value = true

                val reviewsQuery = ParseQuery<ParseObject>("LoggedMovie")

                reviewsQuery.findInBackground { publicReviews, e ->
                    if (publicReviews != null){
                        val publicLoglineReviews = mutableListOf<LoglineReview>()
                        for (log in publicReviews){
                            val content = log.getString("review") ?: ""
                            val date = DateHelper.convertLongToLocalDateTime(log.getNumber("date")?.toLong())
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
                            val profile = profileQuery.find().firstOrNull()
                            if (profile != null){
                                avatarPath = profile.getParseFile("profileImage")?.url ?: ""
                                authorName = profile.getString("userName") ?: "Anonymous"
                            }

                            val review = LoglineReview(
                                authorName = authorName,
                                userId = userId,
                                content = content,
                                movie = movie,
                                createdAt = date,
                                avatarPath = avatarPath,
                                rating = rating
                            )
                            publicLoglineReviews.add(review)
                        }
                        _publicReviews.value = publicLoglineReviews
                        _isLoading.value = false
                    }else if(e != null){
                        Log.e("PublicReviewsViewModel", "Error: ", e)
                    }
                }

            }catch (e: Exception){
                Log.e("PublicReviewsViewModel", "Catch Error: ", e)
            }
        }
    }

}