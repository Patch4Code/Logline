package com.patch4code.loglinemovieapp.features.person_details.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.snapshots.SnapshotStateMap

object LazyRowStatesSaver {
    val saver = Saver<SnapshotStateMap<String, LazyListState>, Map<String, Int>>(
        save = { lazyRowStates ->
            lazyRowStates.mapValues { it.value.firstVisibleItemIndex }.toMap()
        },
        restore = { savedMap ->
            SnapshotStateMap<String, LazyListState>().apply {
                savedMap.forEach { (key, index) ->
                    this[key] = LazyListState(firstVisibleItemIndex = index)
                }
            }
        }
    )
}