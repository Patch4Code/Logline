package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieGenres
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieYears
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortFilterDialog(
    showFilterDialog: MutableState<Boolean>,
    sortOptions: List<SortOption>,
    selectedSortOption: MutableState<SortOption>,
    selectedFilterOptions: MutableState<FilterOptions>,
    onApplyFilters:() -> Unit
){

    if (!showFilterDialog.value) return

    val chipWidth = 72.dp

    val tempSelectedSortOption = remember { mutableStateOf(selectedSortOption.value) }

    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres()
    val selectedGenres = remember{ mutableStateListOf(*selectedFilterOptions.value.selectedGenres.toTypedArray()) }

    val decades = MovieYears.getDecades()
    val selectedDecades = remember { mutableStateListOf(*selectedFilterOptions.value.selectedDecades.toTypedArray()) }
    val years = MovieYears.getYears()
    val selectedYears = remember { mutableStateListOf(*selectedFilterOptions.value.selectedYears.toTypedArray()) }

    val primaryLanguages = MovieLanguages.getPrimaryLanguages()
    val selectedLanguages = remember { mutableStateListOf(*selectedFilterOptions.value.selectedLanguages.toTypedArray()) }
    val showLanguageDialog = remember { mutableStateOf(false) }

    Dialog(onDismissRequest =  {showFilterDialog.value = false},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Sort and Filter",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { showFilterDialog.value = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close Dialog")
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = {
                                tempSelectedSortOption.value = sortOptions.first()
                                selectedGenres.clear()
                                selectedDecades.clear()
                                selectedYears.clear()
                                selectedLanguages.clear() },
                            content = {Text("reset")}
                        )
                    }
                )
                Column(modifier = Modifier.fillMaxSize().padding(16.dp).weight(1f)) {

                    Text("Sort by")
                    FilterSortDropdown(tempSelectedSortOption, sortOptions)

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text("Genre")
                    FilterGenreSelection(availableGenres, selectedGenres)

                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Decade/Year")

                    FilterChip(
                        selected = selectedDecades.isEmpty() && selectedYears.isEmpty(),
                        onClick = {
                            selectedDecades.clear()
                            selectedYears.clear()
                        },
                        label = { Text("Any Year") }
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        decades.forEach{decade->
                            item {
                                FilterChip(
                                    modifier = Modifier.width(chipWidth),
                                    selected = selectedDecades.contains(decade),
                                    onClick = {
                                        if (selectedDecades.contains(decade)) {
                                            selectedDecades.remove(decade)
                                        } else {
                                            selectedDecades.add(decade)
                                        }
                                        selectedYears.clear()
                                    },
                                    label = { Text(text = decade, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        years.forEach{year->
                            item {
                                FilterChip(
                                    modifier = Modifier.width(chipWidth),
                                    selected = selectedYears.contains(year),
                                    onClick = {
                                        if (selectedYears.contains(year)) {
                                            selectedYears.remove(year)
                                        } else {
                                            selectedYears.add(year)
                                        }
                                        selectedDecades.clear()
                                    },
                                    label = { Text(text = year, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Original Language")

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            FilterChip(
                                selected = selectedLanguages.isEmpty(),
                                onClick = {selectedLanguages.clear()},
                                label = { Text("Any Language")},
                            )
                        }
                        val sortedLanguages = primaryLanguages.entries.sortedBy { it.value }
                        sortedLanguages.forEach{language ->
                            item {
                                FilterChip(
                                    label = {Text(language.value)},
                                    selected = selectedLanguages.contains(language.key),
                                    onClick = {
                                        if (selectedLanguages.contains(language.key)) {
                                            selectedLanguages.remove(language.key)
                                        } else {
                                            selectedLanguages.add(language.key)
                                        }
                                    }
                                )
                            }
                        }
                        item {
                            FilterChip(
                                selected = false,
                                onClick = { showLanguageDialog.value = true },
                                label = { Text("Select other")},
                            )
                        }
                    }

                }

                Button(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    onClick = {
                        showFilterDialog.value = false
                        selectedSortOption.value = tempSelectedSortOption.value
                        selectedFilterOptions.value = selectedFilterOptions.value.copy(
                            selectedGenres = selectedGenres,
                            selectedDecades = selectedDecades,
                            selectedYears = selectedYears,
                            selectedLanguages = selectedLanguages
                        )
                        onApplyFilters()
                    },
                    content = { Text("Apply Filters") }
                )
            }
        }

        FilterLanguageDialog(showLanguageDialog, selectedLanguages)
    }
}