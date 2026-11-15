package com.patch4code.logline.features.person_details.presentation.screen_favorite_persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.logline.features.core.domain.model.SortOption
import com.patch4code.logline.features.person_details.domain.model.FavoritePerson
import com.patch4code.logline.room_database.FavoritePersonDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritePersonsViewModel(
    private val favoritePersonDao: FavoritePersonDao
) : ViewModel() {

    private val _sortOption = MutableStateFlow<SortOption>(SortOption.ByNameAsc)
    val sortOption: StateFlow<SortOption> = _sortOption

    private val _filterDepartment = MutableStateFlow<String?>(null)
    val filterDepartment: StateFlow<String?> = _filterDepartment

    val favoritePersons: StateFlow<List<FavoritePerson>> = combine(
        favoritePersonDao.getAllFavoritePersons(),
        _sortOption,
        _filterDepartment
    ) { persons, sort, department ->
        val filtered = if (department != null) {
            persons.filter { it.knownForDepartment == department }
        } else {
            persons
        }

        when (sort) {
            SortOption.ByNameAsc -> filtered.sortedBy { it.name }
            SortOption.ByNameDesc -> filtered.sortedByDescending { it.name }
            else -> filtered.sortedBy { it.name }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableDepartments: StateFlow<List<String>> = favoritePersonDao.getAllFavoritePersons()
        .map { persons -> persons.map { it.knownForDepartment }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSortOption(option: SortOption) {
        _sortOption.value = option
    }

    fun setFilterDepartment(department: String?) {
        _filterDepartment.value = department
    }
}

class FavoritePersonsViewModelFactory(private val favoritePersonDao: FavoritePersonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritePersonsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritePersonsViewModel(favoritePersonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
