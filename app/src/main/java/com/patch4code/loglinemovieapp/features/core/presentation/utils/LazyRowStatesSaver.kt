package com.patch4code.loglinemovieapp.features.core.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.snapshots.SnapshotStateMap

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LazyRowStatesSaver - Utility object for saving
 * and restoring the scroll state of multiple LazyRow components.
 *
 * @author Patch4Code
 */
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