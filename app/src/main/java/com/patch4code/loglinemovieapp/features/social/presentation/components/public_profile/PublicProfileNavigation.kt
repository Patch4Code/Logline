package com.patch4code.loglinemovieapp.features.social.presentation.components.public_profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

@Composable
fun PublicProfileNavigation(userId: String, userName: String, navController: NavController){

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .padding(12.dp)
            .clickable { navController.navigate(Screen.PublicProfileReviewsScreen.route + "/$userId/$userName") }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.Reviews, contentDescription = "Reviews")
                Text(text = "Reviews")
            }
        }
        Box(modifier = Modifier
            .padding(12.dp)
            .clickable { navController.navigate(Screen.PublicProfileListsScreen.route + "/$userId/$userName") }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.AutoMirrored.Filled.FeaturedPlayList, contentDescription = "Lists")
                Text(text = "Lists")
            }
        }
    }
}