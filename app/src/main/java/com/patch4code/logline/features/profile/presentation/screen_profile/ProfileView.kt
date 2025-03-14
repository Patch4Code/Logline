package com.patch4code.logline.features.profile.presentation.screen_profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.logline.R
import com.patch4code.logline.features.navigation.domain.model.Screen
import com.patch4code.logline.features.navigation.presentation.components.topbar_providers.ProvideTopBarTitle
import com.patch4code.logline.features.profile.presentation.components.profile.ExpandableBio
import com.patch4code.logline.features.profile.presentation.components.profile.MovieFavouriteRow
import com.patch4code.logline.features.profile.presentation.components.profile.ProfileHead
import com.patch4code.logline.features.profile.presentation.components.profile.ProfileNavigation
import com.patch4code.logline.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileView - Composable function representing the profile screen view.
 * Displays the user profile.
 *
 * @author Patch4Code
 */
@Composable
fun ProfileView(
    navController: NavController,
    db: LoglineDatabase,
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(db.userProfileDao)
    )
) {

    LaunchedEffect(Unit) {
        profileViewModel.getUserProfileData()
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.ProfileScreen.title.asString())

    val userProfileData = profileViewModel.userProfileData.observeAsState().value

    //Profile Layout
    LazyColumn(horizontalAlignment = CenterHorizontally)
    {
        item{
            ProfileHead(userProfileData?.userProfile)

            //Edit Button
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = { navController.navigate(Screen.ProfileEditScreen.route) }
                ){
                    Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.edit_icon_description))
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            //Username
            Text(text = userProfileData?.userProfile?.username ?: "Anonymous", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.padding(4.dp))

            ProfileNavigation(navController)

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
                    ExpandableBio(text = userProfileData?.userProfile?.bioText ?: "")
                    Spacer(modifier = Modifier.padding(8.dp))
                    MovieFavouriteRow(navController, userProfileData?.favouriteMovies ?: emptyList(), profileViewModel)
            }
        }

    }
}