package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.domain.model.userDataList

class MyMoviesViewModel: ViewModel() {

    private val _myUserDataList = MutableLiveData<List<MovieUserData>>()
    val myUserDataList: LiveData<List<MovieUserData>> get() = _myUserDataList

    init {
        setUserdataList()
    }

    private fun setUserdataList() {
        _myUserDataList.value = userDataList
    }
}