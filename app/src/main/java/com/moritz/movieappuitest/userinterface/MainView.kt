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
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.dataclasses.BottomNavigationItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(){

    val bottomBarItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
        ),
        BottomNavigationItem(
            title = "Social",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val categories = listOf("Movie1", "Movie2", "Movie3", "Movie4", "Movie5", "Movie6")
    val groups = listOf("New Movies", "New from Friends", "Top Rated")

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 TopAppBar (
                     title = {
                         Text(text = "Home", color = Color.White, fontWeight = FontWeight.Bold)
                             },
                     navigationIcon = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Navigation Drawer")
                         }
                     },
                     actions = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                         }
                     },
                     scrollBehavior = scrollBehavior
                 )
        },
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            // navController.navigate(item.title)
                        },
                        label = {
                            Text(text = item.title, color = Color.White)
                        },
                        alwaysShowLabel = false,
                        icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                        }
                    )
                }
            }
        },
        content = { padding ->
            //Home Layout
            LazyColumn(
                modifier = Modifier.padding(padding)
            ){
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
    )
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