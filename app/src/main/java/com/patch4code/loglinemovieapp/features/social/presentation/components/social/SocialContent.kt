package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.datastore.StoreUserData
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialContent(savedLoginData: StoreUserData, socialViewModel: SocialViewModel){

    val context = LocalContext.current

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Card {
                Column (modifier = Modifier.padding(8.dp)){
                    Text(text = "Account", style = MaterialTheme.typography.titleMedium)
                    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = null, modifier = Modifier.size(50.dp))
                        Column (modifier = Modifier.padding(8.dp)){
                            Text(text = "${savedLoginData.getUsername.collectAsState(initial = "").value}")
                            Text(text = "${savedLoginData.getEmail.collectAsState(initial = "").value}")
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Button(
                        onClick = {
                            socialViewModel.logout(
                                onLogoutSuccessful = { Toast.makeText(context, "Successful Logged Out", Toast.LENGTH_LONG).show() },
                                onLogoutError = { Toast.makeText(context, "Logout Error: ${it!!.message}", Toast.LENGTH_LONG).show() }
                            )
                        }
                    ) {
                        Text(text = "Log Out")
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Card(onClick = { /*TODO*/ }) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.Groups2, contentDescription = null, modifier = Modifier.size(50.dp))
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Explore Public Accounts", Modifier.weight(1f))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Card(onClick = { /*TODO*/ }) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Default.Reviews, contentDescription = null, modifier = Modifier.size(50.dp))
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Explore Public Reviews", Modifier.weight(1f))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Card(onClick = { /*TODO*/ }) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.AutoMirrored.Filled.FeaturedPlayList, contentDescription = null, modifier = Modifier.size(50.dp))
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Explore Public Lists", Modifier.weight(1f))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
                }
            }
        }
    }
}