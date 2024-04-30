package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import java.net.URLEncoder

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsTableItem - Composable function representing an item in the lists table.
 * Navigates to the ListView on click.
 *
 * @author Patch4Code
 */
@Composable
fun ListsTableItem(navController: NavController, list: MovieList){

    val jsonMovieList = list.toJson()
    val encodedJsonMovieList = URLEncoder.encode(jsonMovieList, "UTF-8")

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(8.dp)
                .clickable {
                    navController.navigate(Screen.ListScreen.withArgs(encodedJsonMovieList))
                }
        ){
            ListsItemPreviewImages(list)

            Column (modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)){
                Text(text = "${list.name} (${list.movies.size})", style = MaterialTheme.typography.titleMedium)
            }
        }
        //HorizontalDivider(modifier = Modifier.padding(start = 8.dp))
    }
}

