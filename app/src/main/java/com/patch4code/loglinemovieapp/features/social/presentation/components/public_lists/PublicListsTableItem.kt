package com.patch4code.loglinemovieapp.features.social.presentation.components.public_lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item.ListsItemPreviewImages
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.social.domain.model.PublicList
import java.net.URLEncoder

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicListsTableItem - Composable function that displays an item of the public list table.
 *
 * @author Patch4Code
 */
@Composable
fun PublicListsTableItem(navController: NavController, publicList: PublicList, isProfileListItem: Boolean = false){

    val isProfilePublic = publicList.isProfilePublic
    val userId = publicList.userId
    val userName = publicList.authorName

    Column (modifier = Modifier
        .clickable {
            val publicListJson = publicList.toJson()
            val encodedPublicListJson = URLEncoder.encode(publicListJson, "UTF-8")
            navController.navigate(Screen.PublicListScreen.withArgs(encodedPublicListJson))
        }
    ){

        // only displays user info if this is no profile list item
        if(!isProfileListItem){
            Row (modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp), verticalAlignment = Alignment.CenterVertically){
                AsyncImage(
                    model = publicList.avatarPath,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .zIndex(2f)
                        .clickable (isProfilePublic){
                            navController.navigate(Screen.PublicProfileScreen.route + "/$userId/$userName") },
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = if(isProfilePublic) R.drawable.default_profile_image else R.drawable.person_placeholder)
                )
                Text(text = publicList.authorName, style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(110.dp)
        ){
            ListsItemPreviewImages(publicList.movieList)

            Column (modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)){
                Text(text = "${publicList.movieList.name} (${publicList.movieList.movies.size})", style = MaterialTheme.typography.titleMedium)
            }
        }
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}