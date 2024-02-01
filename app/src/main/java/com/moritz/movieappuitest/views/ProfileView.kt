package com.moritz.movieappuitest.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.getUserProfileData
import com.moritz.movieappuitest.views.profile.ExpandableBio
import com.moritz.movieappuitest.views.profile.MovieFavouriteRow
import com.moritz.movieappuitest.views.profile.ProfileHead
import com.moritz.movieappuitest.views.profile.ProfileNavigation

@Composable
fun ProfileView(navController: NavController) {

    val userData = remember { mutableStateOf(getUserProfileData()) }

    //Profile Layout
    Column(horizontalAlignment = CenterHorizontally)
    {
        ProfileHead(userData)

        //Edit Button
        IconButton(modifier = Modifier.align(End), onClick = { navController.navigate(Screen.ProfileEditScreen.route) }
        ){
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        //Username
        Text(
            text = userData.value.username,
            color = Color.White,
            modifier = Modifier.align(CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        //Friends
        Text(text = userData.value.numberOfFriends.toString() +  " Friends",
            modifier = Modifier
                .clickable { navController.navigate(Screen.FriendsScreen.route) }
                .align(CenterHorizontally))

        ProfileNavigation(navController)

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