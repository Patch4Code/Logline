package com.moritz.movieappuitest.views.list.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.dataclasses.MovieList

@Composable
fun ListsItem(navController: NavController, list: MovieList){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(8.dp)
            .clickable {
                //navController.navigate(Screen.)
            }
    ){
        ListsItemPreviewImages(list)

        Column (modifier = Modifier
            .padding(start = 8.dp)
            .weight(1f)){
            Text(text = list.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
        }
        Column(modifier = Modifier.padding(8.dp)){
            Icon(
                imageVector = if(list.isPublic) Icons.Default.Public else Icons.Default.Lock,
                contentDescription = if(list.isPublic) "List is public" else "List is private",
            )
        }
    }
    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
}

