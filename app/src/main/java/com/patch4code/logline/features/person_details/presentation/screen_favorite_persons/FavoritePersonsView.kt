package com.patch4code.logline.features.person_details.presentation.screen_favorite_persons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.utils.MovieHelper
import com.patch4code.logline.features.navigation.domain.model.Screen
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.logline.features.person_details.domain.model.FavoritePerson
import com.patch4code.logline.room_database.LoglineDatabase

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarSortFilterActions
import com.patch4code.logline.features.person_details.presentation.components.FavoritePersonsSortFilterDialog

@Composable
fun FavoritePersonsView(
    navController: NavController,
    db: LoglineDatabase,
    viewModel: FavoritePersonsViewModel = viewModel(
        factory = FavoritePersonsViewModelFactory(db.favoritePersonDao)
    )
) {
    val showBottomSheet = remember { mutableStateOf(false) }

    ProvideTopBarTitle(title = Screen.FavoritePersonsScreen.title.asString())
    ProvideTopBarSortFilterActions(sortFilterOnClickAction = { showBottomSheet.value = true })

    val favoritePersons by viewModel.favoritePersons.collectAsState(initial = emptyList())
    val selectedSortOption by viewModel.sortOption.collectAsState()
    val selectedFilterDepartment by viewModel.filterDepartment.collectAsState()
    val availableDepartments by viewModel.availableDepartments.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(favoritePersons) { person ->
            FavoritePersonItem(person) {
                navController.navigate(Screen.PersonDetailsScreen.route + "/${person.id}")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    FavoritePersonsSortFilterDialog(
        showFilterDialog = showBottomSheet,
        selectedSortOption = selectedSortOption,
        selectedFilterDepartment = selectedFilterDepartment,
        availableDepartments = availableDepartments,
        onApply = { sortOption, filterDepartment ->
            viewModel.setSortOption(sortOption)
            viewModel.setFilterDepartment(filterDepartment)
        }
    )
}

@Composable
fun FavoritePersonItem(person: FavoritePerson, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .width(66.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                AsyncImage(
                    model = MovieHelper.processPosterUrl(person.profilePath),
                    contentDescription = person.name,
                    error = painterResource(id = R.drawable.movie_poster_placeholder)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = person.name, style = MaterialTheme.typography.titleLarge)
                Text(text = person.knownForDepartment, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
