package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieYears
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverDecadeYearSelection(discoverOptions: MutableState<DiscoverOptions>){

    val discoverDecades = MovieYears.getDiscoverDecades()
    val years = MovieYears.getYears()

    val chipWidth = 72.dp

    // any selection
    FilterChip(
        selected = discoverOptions.value.primaryReleaseYear == null &&
                discoverOptions.value.primaryReleaseDateGte == null &&
                discoverOptions.value.primaryReleaseDateLte == null,
        onClick = {
            discoverOptions.value = discoverOptions.value.clearAllPrimaryReleases()
        },
        label = { Text("Any Year") }
    )

    Spacer(modifier = Modifier.padding(4.dp))

    // decade selection
    BaseFilterChipRow(
        items = discoverDecades,
        labelProvider = { it.decade },
        isSelected = {discoverDecade ->
            discoverOptions.value.primaryReleaseDateGte == discoverDecade.decadeStartDate &&
                    discoverOptions.value.primaryReleaseDateLte == discoverDecade.decadeEndDate
        },
        onItemToggle = {discoverDecade->
            if (discoverOptions.value.primaryReleaseDateGte == discoverDecade.decadeStartDate &&
                discoverOptions.value.primaryReleaseDateLte == discoverDecade.decadeEndDate
            ) {
                discoverOptions.value = discoverOptions.value.clearAllPrimaryReleases()
            } else {
                discoverOptions.value = discoverOptions.value.copy(
                    primaryReleaseDateGte = discoverDecade.decadeStartDate,
                    primaryReleaseDateLte = discoverDecade.decadeEndDate
                )
                discoverOptions.value = discoverOptions.value.clearPrimaryReleaseYear()
            }
        },
        modifier = Modifier.width(chipWidth)
    )

    Spacer(modifier = Modifier.padding(4.dp))

    // year selection
    BaseFilterChipRow(
        items = years,
        labelProvider = {year -> year },
        isSelected = {year ->
            discoverOptions.value.primaryReleaseYear == year.toInt()
        },
        onItemToggle = {year ->
            if (discoverOptions.value.primaryReleaseYear == year.toInt()) {
                discoverOptions.value = discoverOptions.value.clearAllPrimaryReleases()
            } else {
                discoverOptions.value = discoverOptions.value.copy(
                    primaryReleaseYear = year.toInt()
                )
                discoverOptions.value = discoverOptions.value.clearPrimaryReleaseDates()
            }
        },
        modifier = Modifier.width(chipWidth)
    )
}