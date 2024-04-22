package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.items.ListItem
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.social.domain.model.PublicList
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_lists.DeletePublicListDialog
import java.net.URLDecoder

@Composable
fun PublicListView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    publicListJson: String?,
    publicListViewModel: PublicListViewModel = viewModel()
){
    val decodedPublicListJson = URLDecoder.decode(publicListJson, "UTF-8")
    val publicList: PublicList = JSONHelper.fromJson(decodedPublicListJson)

    val isProfilePublic = publicList.isProfilePublic
    val userId = publicList.userId
    val userName = publicList.authorName

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicListScreen)
        navViewModel.overrideCurrentScreenTitle("List by ${publicList.authorName}")
        publicListViewModel.isYourReview(publicList.userId)
    }

    val isYourReview = publicListViewModel.isYourList.observeAsState().value

    val showDeletePublicListDialog = remember { mutableStateOf(false) }

    Column{

        Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically){
                AsyncImage(
                    model = publicList.avatarPath,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .zIndex(2f)
                        .clickable(isProfilePublic) {
                            navController.navigate(Screen.PublicProfileScreen.route + "/$userId/$userName") },
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = if(isProfilePublic) R.drawable.default_profile_image else R.drawable.person_placeholder)
                )
                Text(text = publicList.authorName, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = publicList.movieList.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge
                )
                if(isYourReview == true){
                    IconButton(onClick = { showDeletePublicListDialog.value = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Public List")
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            item {
                publicList.movieList.movies.forEach{movie->
                    ListItem(navController, movie)
                }
            }
        }
    }

    DeletePublicListDialog(showDeletePublicListDialog, publicList.objectId, navController, publicListViewModel)
}