package com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun ProfileEditView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(db.userProfileDao)
    )
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ProfileEditScreen)
        profileViewModel.getUserProfileData()
    }

    val context = LocalContext.current
    val userProfile = profileViewModel.userProfileData.observeAsState().value

    val profileImageName = userProfile?.profileImagePath ?: UserProfile.DEFAULT_PROFILE_IMAGE_PATH
    val bannerImageName = userProfile?.bannerImagePath ?: UserProfile.DEFAULT_BANNER_IMAGE_PATH

    val profileImageResourceId = context.resources.getIdentifier(profileImageName, "drawable", context.packageName)
    val bannerImageResourceId = context.resources.getIdentifier(bannerImageName, "drawable", context.packageName)




    LazyColumn(modifier = Modifier.padding(16.dp))
    {
        item {
            Text(text = "Profile Name", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
            ElevatedButton(onClick = { /*TODO*/ }) {
                Text(text = userProfile?.username ?: "Anonymous")
                Spacer(modifier = Modifier.padding(4.dp))
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            Text(text = "Bio", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
            ElevatedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = userProfile?.bioText ?: "",
                    maxLines = 3, minLines = 3, modifier = Modifier.width(250.dp), textAlign = TextAlign.Center
                )
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.weight(1f))
            }

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))


            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Profile Image", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
                    Card(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(120.dp)
                            .width(120.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape)
                    ){
                        Image(
                            painter = painterResource(id = profileImageResourceId),
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Reset to Default")
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Column (){
                    Text(text = "Banner Image", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
                    Card (onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                            .border(width = 2.dp, color = Color.DarkGray)
                    ){
                        Image(
                            painter = painterResource(bannerImageResourceId),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "Banner")
                    }
                    TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = "Reset to Default")
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            Text(text = "Favourite Movies", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            ){
                userProfile?.favouriteMovies?.forEach { movie->
                    val movieId = movie.id
                    val moviePosterUrl = TmdbCredentials.POSTER_URL + movie.posterUrl
                    AsyncImage(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable {
                                if(movieId >= 0){
                                    navController.navigate(Screen.MovieScreen.withArgs(movieId.toString()))
                                } else{
                                    //open dialog to select movie
                                }
                            },
                        model = moviePosterUrl,
                        contentDescription = movie.title,
                        error = painterResource(id = R.drawable.add_favourite_movie)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}