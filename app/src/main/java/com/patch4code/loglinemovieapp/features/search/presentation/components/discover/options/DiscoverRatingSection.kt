package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.components.utils.DiscoverHelper.formatRatingText
import kotlin.math.round

@Composable
fun DiscoverRatingSection(discoverOptions: MutableState<DiscoverOptions>) {

    var sliderStart by rememberSaveable { mutableFloatStateOf(discoverOptions.value.voteAverageGte ?: 0f) }
    var sliderEnd by rememberSaveable { mutableFloatStateOf(discoverOptions.value.voteAverageLte ?: 10f) }

    val sliderPosition = sliderStart..sliderEnd

    LaunchedEffect(discoverOptions.value) {
        sliderStart = discoverOptions.value.voteAverageGte ?: 0f
        sliderEnd = discoverOptions.value.voteAverageLte ?: 10f
    }

    Text(formatRatingText(sliderStart, sliderEnd))

    RangeSlider(
        value = sliderPosition ,
        onValueChange = {newValues ->
            sliderStart = newValues.start
            sliderEnd = newValues.endInclusive

            discoverOptions.value = discoverOptions.value.copy(
                voteAverageGte = round(sliderStart),
                voteAverageLte = round(sliderEnd)
            )
        },
        valueRange = 0f..10f,
        steps = 9
    )
}