package com.patch4code.loglinemovieapp.features.my_movies.presentation.components

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MyMoviesSortBottomSheet - Composable function that displays a bottom sheet for sorting MyMovies.
 *
 * @author Patch4Code
 */
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMoviesSortBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    selectedSortOption: MutableState<MyMoviesSortOption>,
    myMoviesViewModel: MyMoviesViewModel
){
    if(!showBottomSheet.value) return

    ModalBottomSheet(
        onDismissRequest = {showBottomSheet.value = false},
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        scrimColor = Color.Transparent
    ){

        val sortOptions = MyMoviesSortOption.entries

        Column (modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)){
            Text(text = "Sort by", style = MaterialTheme.typography.titleLarge)
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))

            sortOptions.forEach { sortOption ->
                Row {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedSortOption.value = sortOption
                            myMoviesViewModel.loadWatchedMovies(selectedSortOption.value)
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

 */