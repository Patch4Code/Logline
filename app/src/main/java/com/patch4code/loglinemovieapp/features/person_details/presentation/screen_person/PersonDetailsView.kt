package com.patch4code.loglinemovieapp.features.person_details.presentation.screen_person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.ExpandableText
import com.patch4code.loglinemovieapp.features.core.presentation.components.cards.MovieRowBrowseCard
import com.patch4code.loglinemovieapp.features.core.presentation.components.load.LoadErrorDisplay
import com.patch4code.loglinemovieapp.features.core.presentation.components.load.LoadingIndicator
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.SortOptionSaver
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarSortActions
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.person_details.presentation.components.PersonDetailsSortBottomSheet
import com.patch4code.loglinemovieapp.features.person_details.presentation.utils.LazyRowStatesSaver

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PersonDetailsView - Composable function representing the person-details screen view.
 * Displays details of a person (cast/crew) including their biography, image,
 * and movie credits (films in which they were involved).
 *
 * @author Patch4Code
 */
@Composable
fun PersonDetailsView(
    navController: NavController,
    personDetailsViewModel: PersonDetailsViewModel = viewModel(),
    id: Int?
){

    val personId = id?: -1

    val selectedSortOption : MutableState<SortOption> =
        rememberSaveable(stateSaver = SortOptionSaver.saver) { mutableStateOf(SortOption.ByPopularityDesc) }
    val showBottomSheet = remember { mutableStateOf(false)  }

    val pageListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val lazyRowStates = rememberSaveable(saver = LazyRowStatesSaver.saver) { SnapshotStateMap() }

    LaunchedEffect(Unit) {
        personDetailsViewModel.loadPersonDetails(personId)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.PersonDetailsScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortActions(onClickAction = {showBottomSheet.value = true})

    val personDetails = personDetailsViewModel.personDetails.observeAsState().value

    DisposableEffect(personDetails) {
        if (personDetails != null) {
            val mainDepartment = personDetails.knownForDepartment
            personDetailsViewModel.loadPersonMovieCredits(personId, mainDepartment, selectedSortOption.value)
        }
        onDispose {}
    }

    val personCreditsMap = personDetailsViewModel.personCreditsMap.observeAsState().value

    val isLoading by personDetailsViewModel.isLoading.observeAsState(initial = false)
    val hasLoadError by personDetailsViewModel.hasLoadError.observeAsState(initial = false)

    if(isLoading){
        LoadingIndicator()
    }else if(hasLoadError){
        LoadErrorDisplay(onReload = { personDetailsViewModel.loadPersonDetails(personId) })
    }
    else{
        LazyColumn(modifier = Modifier.padding(16.dp), state = pageListState){
            item{
                Row(modifier = Modifier.padding(bottom = 16.dp)){
                    // person image
                    Card (modifier = Modifier
                        .height(200.dp)
                        .width(133.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        AsyncImage(
                            model = MovieHelper.processPosterUrl(personDetails?.profilePath),
                            contentDescription = "${stringResource(id = R.string.person_image_description)} ${personDetails?.name}",
                            error = painterResource(id = R.drawable.movie_poster_placeholder)
                        )
                    }

                    Column (modifier = Modifier.height(200.dp).padding(8.dp)){
                        // person name and main department
                        Text(text = personDetails?.name ?: "N/A", style = MaterialTheme.typography.headlineMedium)
                        Text(text = "${stringResource(id = R.string.known_for_title)} ${personDetails?.knownForDepartment}")
                    }
                }
                // person bio text
                Text(text = stringResource(id = R.string.bio_title))
                ExpandableText(text = personDetails?.biography ?: "N/A", maxLinesCollapsed = 4)
                Spacer(modifier = Modifier.padding(top = 16.dp))

                // person movie credits grouped by department
                personCreditsMap?.forEach { (groupName, movies) ->
                    Text(text = groupName,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold)

                    val lazyRowState = lazyRowStates.getOrPut(groupName) { LazyListState() }
                    LazyRow(state = lazyRowState) {
                        items(movies) { movie ->
                            MovieRowBrowseCard(navController, movie)
                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                    }
                    Spacer(Modifier.padding(8.dp))
                }
            }
        }
        if (personDetails != null) {
            PersonDetailsSortBottomSheet(showBottomSheet, selectedSortOption, personDetails.knownForDepartment, personDetailsViewModel)
        }
    }
}