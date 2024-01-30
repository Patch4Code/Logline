package com.moritz.movieappuitest.userinterface.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.dataclasses.ProfileNavigationElement
import com.moritz.movieappuitest.dataclasses.getUserProfileData
import com.moritz.movieappuitest.userinterface.ui_elements.ExpandableBio
import com.moritz.movieappuitest.userinterface.ui_elements.MovieFavouriteRow

@Composable
fun ProfileView(navController: NavController) {

    val userData = remember { mutableStateOf(getUserProfileData()) }

    //Profile Layout
    Column(horizontalAlignment = CenterHorizontally)
    {
        Box {
            AsyncImage(
                model = userData.value.bannerImageUrl,
                contentDescription = "Banner"
            )
            AsyncImage(
                model = userData.value.profileImageUrl,
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
        IconButton(modifier = Modifier.align(End), onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = userData.value.username,
            color = Color.White,
            modifier = Modifier.align(CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = userData.value.numberOfFriends.toString(),
            Modifier
                .clickable { }
                .align(CenterHorizontally))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileNavigationElement().getProfileNavigationElements().forEach { item ->
                Box(modifier = Modifier
                    .padding(12.dp)
                    .clickable { }) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = item.navIcon, contentDescription = item.title)
                        Text(text = item.title, color = Color.White)
                    }
                }
            }
        }
        LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
            item{
                ExpandableBio(text = userData.value.bioText)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "FAVOURITE MOVIES")
                MovieFavouriteRow(navController, userData.value.favouriteMovies)
            }
        }
    }
}


