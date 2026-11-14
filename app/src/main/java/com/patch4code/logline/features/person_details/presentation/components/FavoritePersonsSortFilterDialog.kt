package com.patch4code.logline.features.person_details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.SortOption
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseDropdown
import com.patch4code.logline.features.core.presentation.components.filter_dialog.FilterApplyButton
import com.patch4code.logline.features.core.presentation.components.filter_dialog.FilterSortDropdown
import com.patch4code.logline.features.core.presentation.components.filter_dialog.FilterTopBarSection
import com.patch4code.logline.features.person_details.domain.model.FavoritePersonsSortFilterOptions

@Composable
fun FavoritePersonsSortFilterDialog(
    showFilterDialog: MutableState<Boolean>,
    selectedSortOption: SortOption,
    selectedFilterDepartment: String?,
    availableDepartments: List<String>,
    onApply: (SortOption, String?) -> Unit
) {
    if (!showFilterDialog.value) return

    val tempSelectedSortOption = remember { mutableStateOf(selectedSortOption) }
    val tempSelectedFilterDepartment = remember { mutableStateOf(selectedFilterDepartment) }

    val sortOptions = FavoritePersonsSortFilterOptions.sortOptions

    // Add "All" to the list of departments for the dropdown
    val departmentOptions = listOf("All") + availableDepartments

    Dialog(onDismissRequest = { showFilterDialog.value = false },
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
                        tempSelectedFilterDepartment.value = null
                    }
                )

                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp).weight(1f)) {
                    item {
                        Text(stringResource(id = R.string.sort_by_text))
                        FilterSortDropdown(tempSelectedSortOption, sortOptions)

                        Spacer(modifier = Modifier.padding(8.dp))

                        if (availableDepartments.isNotEmpty()) {
                            Text("Filter by Department")
                            BaseDropdown(
                                selectedItem = tempSelectedFilterDepartment.value ?: "All",
                                items = departmentOptions,
                                labelProvider = { it },
                                onItemSelected = {
                                    if (it == "All") {
                                        tempSelectedFilterDepartment.value = null
                                    } else {
                                        tempSelectedFilterDepartment.value = it
                                    }
                                }
                            )
                        }
                    }
                }

                FilterApplyButton {
                    onApply(tempSelectedSortOption.value, tempSelectedFilterDepartment.value)
                    showFilterDialog.value = false
                }
            }
        }
    }
}
