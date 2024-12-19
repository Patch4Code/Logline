package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

@Composable
fun SortFilterDialog(
    showFilterDialog: MutableState<Boolean>,
    sortOptions: List<SortOption>,
    selectedSortOption: MutableState<SortOption>,
    selectedFilterOptions: MutableState<FilterOptions>,
    onApplyFilters:() -> Unit
){

    if (!showFilterDialog.value) return

    val tempSelectedSortOption = remember { mutableStateOf(selectedSortOption.value) }
    val selectedGenres = remember{ mutableStateListOf(*selectedFilterOptions.value.selectedGenres.toTypedArray()) }
    val selectedDecades = remember { mutableStateListOf(*selectedFilterOptions.value.selectedDecades.toTypedArray()) }
    val selectedYears = remember { mutableStateListOf(*selectedFilterOptions.value.selectedYears.toTypedArray()) }
    val selectedLanguages = remember { mutableStateListOf(*selectedFilterOptions.value.selectedLanguages.toTypedArray()) }

    Dialog(onDismissRequest =  {showFilterDialog.value = false},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                FilterTopBarSection(
                    onCloseDialog = { showFilterDialog.value = false },
                    onResetClick = {
                        tempSelectedSortOption.value = sortOptions.first()
                        selectedGenres.clear()
                        selectedDecades.clear()
                        selectedYears.clear()
                        selectedLanguages.clear()
                    }
                )

                Column(modifier = Modifier.fillMaxSize().padding(16.dp).weight(1f)) {

                    Text("Sort by")
                    FilterSortDropdown(tempSelectedSortOption, sortOptions)

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text("Genre")
                    FilterGenreSelection(selectedGenres)

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text("Decade/Year")
                    FilterDecadeYearSelection(selectedDecades, selectedYears)

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text("Original Language")
                    FilterLanguageSection(selectedLanguages)
                }

                FilterApplyButton{
                    showFilterDialog.value = false
                    selectedSortOption.value = tempSelectedSortOption.value
                    selectedFilterOptions.value = selectedFilterOptions.value.copy(
                        selectedGenres = selectedGenres,
                        selectedDecades = selectedDecades,
                        selectedYears = selectedYears,
                        selectedLanguages = selectedLanguages)
                    onApplyFilters()
                }
            }
        }
    }
}