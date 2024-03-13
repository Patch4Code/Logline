package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R

@Composable
fun AsyncImageCutPoster(url:String?){
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .clipToBounds()
            .layout { measurable, constraints ->
                val width = (30 * density).toInt()

                val placeable = measurable.measure(
                    constraints
                )
                layout(width, placeable.height) {
                    placeable.place(-100, 0)
                }
            },
        error = painterResource(id = R.drawable.poster_placeholder)
    )
}