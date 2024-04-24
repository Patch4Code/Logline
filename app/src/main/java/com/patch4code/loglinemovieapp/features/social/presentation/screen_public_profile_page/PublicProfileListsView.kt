package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.social.presentation.components.public_lists.PublicListsTableItem

@Composable
fun PublicProfileListsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    userId: String?,
    userName: String?,
    publicProfileListsViewModel: PublicProfileListsViewModel = viewModel()
){
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PublicProfileListsScreen)
        navViewModel.overrideCurrentScreenTitle("$userName${UiText.StringResource(R.string.user_lists_appendage).asString(context)}")
        publicProfileListsViewModel.getPublicProfileLists(userId)
    }

    val isLoading by publicProfileListsViewModel.isLoading.observeAsState(initial = false)
    val publicProfileLists = publicProfileListsViewModel.publicProfileLists.observeAsState().value

    if(isLoading){
        LoadingIndicator()
    }else{
        if (publicProfileLists.isNullOrEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.no_lists_text))
            }
            return
        }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(publicProfileLists)
            { publicProfileList ->
                PublicListsTableItem(navController, publicProfileList, true)
            }
        }
    }
}