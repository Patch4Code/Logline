package com.moritz.movieappuitest.userinterface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.R


@Composable
fun MainView(navController: NavController){

    val categories = listOf("Movie1", "Movie2", "Movie3", "Movie4", "Movie5", "Movie6")
    val groups = listOf("New Movies", "New from Friends", "Top Rated")

    LazyColumn {
        groups.forEach{ group ->
            item {
                Text(
                    text = group,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
                LazyRow {
                    items(categories){
                            cat->
                        BrowserItem(cat = cat, drawable = R.drawable.apps)
                    }
                }
            }
        }
    }
}

@Composable
fun BrowserItem(cat: String, drawable: Int) {
    Card(modifier = Modifier
        .padding(16.dp)
        .size(200.dp),
        border = BorderStroke(3.dp, color = Color.DarkGray))
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = cat)
            Image(painter = painterResource(id = drawable), contentDescription = cat)
        }
    }
}