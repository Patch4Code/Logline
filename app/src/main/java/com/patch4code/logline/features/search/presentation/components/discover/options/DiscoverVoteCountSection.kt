package com.patch4code.logline.features.search.presentation.components.discover.options

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.search.domain.model.DiscoverOptions
import com.patch4code.logline.features.search.presentation.components.utils.DiscoverHelper.formatNumber
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverVoteCountSection - Composable function
 * for selecting a minimum vote count in the discovery filter.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverVoteCountSection(discoverOptions: MutableState<DiscoverOptions>) {

    val voteCountValues = listOf(0f, 100f, 1_000f, 10_000f)

    var sliderPosition by rememberSaveable {
        mutableFloatStateOf(
            voteCountValues.indexOfFirst { it == discoverOptions.value.voteCountGte }
                .takeIf { it != -1 }?.toFloat() ?: 0f
        )
    }

    LaunchedEffect(discoverOptions.value.voteCountGte) {
        if (discoverOptions.value.voteCountGte == 100f){
            sliderPosition = 1f
        }
    }

    Text("${stringResource(id = R.string.vote_count_text)} (${formatNumber(discoverOptions.value.voteCountGte?.toInt())}+)")

    Slider(
        value = sliderPosition,
        onValueChange = {newValue ->
            sliderPosition = round(newValue)
            discoverOptions.value = discoverOptions.value.copy(
                voteCountGte = voteCountValues[newValue.roundToInt()]
            )
        },
        valueRange = 0f..(voteCountValues.size - 1).toFloat(),
        steps = voteCountValues.size - 2
    )
}