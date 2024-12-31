package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import android.util.Log
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.components.utils.DiscoverHelper.formatNumber
import kotlin.math.round
import kotlin.math.roundToInt

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

    LaunchedEffect(Unit) {
        Log.e("DiscoverVoteCountSection", "sliderPosition: $sliderPosition, count: ${discoverOptions.value.voteCountGte}")
    }

    Text("VoteCount (${formatNumber(discoverOptions.value.voteCountGte?.toInt())}+)")

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