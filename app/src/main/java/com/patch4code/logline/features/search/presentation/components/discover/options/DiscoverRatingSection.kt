package com.patch4code.logline.features.search.presentation.components.discover.options

import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.patch4code.logline.features.search.domain.model.DiscoverOptions
import com.patch4code.logline.features.search.presentation.components.utils.DiscoverHelper.formatRatingText
import kotlin.math.round

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverRatingSection - composable function for
 * filtering movies based on average rating ranges.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverRatingSection(discoverOptions: MutableState<DiscoverOptions>) {

    val context = LocalContext.current

    var sliderStart by rememberSaveable { mutableFloatStateOf(discoverOptions.value.voteAverageGte ?: 0f) }
    var sliderEnd by rememberSaveable { mutableFloatStateOf(discoverOptions.value.voteAverageLte ?: 10f) }

    val sliderPosition = sliderStart..sliderEnd

    LaunchedEffect(discoverOptions.value) {
        sliderStart = discoverOptions.value.voteAverageGte ?: 0f
        sliderEnd = discoverOptions.value.voteAverageLte ?: 10f
    }

    Text(formatRatingText(sliderStart, sliderEnd, context))

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