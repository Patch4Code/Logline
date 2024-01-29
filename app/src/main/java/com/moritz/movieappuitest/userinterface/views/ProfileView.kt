package com.moritz.movieappuitest.userinterface.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ProfileView(navController: NavController) {

    //Profile Layout
    Column()
    {
        Box {
            AsyncImage(
                model = "https://64.media.tumblr.com/60d40dcf8b890fa9278e6d0658b80f72/58e5a6f8f2be5db3-58/s1280x1920/40cbce0f93616aa0ead8ca3c6367d3c26ac3e82e.jpg",
                contentDescription = "Banner"
            )
            AsyncImage(
                model = "https://media.gq-magazin.de/photos/5c9cdb109d77084a7e9ed707/1:1/w_800,h_800,c_limit/teaser-fight-club-quer.jpg",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .align(Center)
                    .offset(y = 60.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.padding(35.dp))
        Text(
            text = "@Tylor Durden",
            color = Color.White,
            modifier = Modifier.align(CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "2 Friends",
            Modifier
                .clickable { }
                .align(CenterHorizontally))

        Row (modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.Center){

            Box(modifier = Modifier.padding(12.dp)){
                Column {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Movies")
                    }
                    Text(text = "Movies", color = Color.White)
                }
            }
            Box(modifier = Modifier.padding(12.dp)){
                Column {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Diary")
                    }
                    Text(text = "Diary", color = Color.White)
                }
            }
            Box(modifier = Modifier.padding(12.dp)){
                Column {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.List, contentDescription = "Watchlist")
                    }
                    Text(text = "Watchlist", color = Color.White)
                }
            }
            Box(modifier = Modifier.padding(12.dp)){
                Column {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Lists")
                    }
                    Text(text = "Lists", color = Color.White)
                }
            }
        }
    }
}


