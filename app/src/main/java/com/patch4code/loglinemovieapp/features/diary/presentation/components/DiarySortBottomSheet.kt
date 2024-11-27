package com.patch4code.loglinemovieapp.features.diary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.diary.domain.model.DiarySortOptions
import com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary.DiaryViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiarySortBottomSheet - Composable function that displays a bottom sheet for sorting the diary.
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarySortBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    selectedSortOption: MutableState<DiarySortOptions>,
    diaryViewModel: DiaryViewModel
){

    if(!showBottomSheet.value) return

    ModalBottomSheet(
        onDismissRequest = {showBottomSheet.value = false},
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        scrimColor = Color.Transparent
    ){

        val sortOptions = DiarySortOptions.entries

        Column (modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)){
            Text(text = "Sort by", style = MaterialTheme.typography.titleLarge)
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))

            sortOptions.forEach { sortOption ->
                Row {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedSortOption.value = sortOption
                            diaryViewModel.getDiaryLogs(selectedSortOption.value)
                        }
                    ){
                        Text(text = sortOption.label, modifier = Modifier.weight(1f))
                        if (sortOption == selectedSortOption.value) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}