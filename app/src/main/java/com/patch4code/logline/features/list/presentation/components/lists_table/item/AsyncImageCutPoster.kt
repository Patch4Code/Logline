package com.patch4code.logline.features.list.presentation.components.lists_table.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.patch4code.logline.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * AsyncImageCutPoster - Composable function displaying an image asynchronously with a clipped width,
 * used for ListsItemPreviewImages
 *
 * @author Patch4Code
 */
@Composable
fun AsyncImageCutPoster(url:String?){
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .clipToBounds()
            .layout { measurable, constraints ->
                val width = (30 * density).toInt()

                val placeable = measurable.measure(constraints)

                layout(width, placeable.height) {
                    placeable.place(-100, 0)
                }
            },
        error = painterResource(id = if(url != null) R.drawable.movie_poster_placeholder else R.drawable.poster_placeholder)
    )
}