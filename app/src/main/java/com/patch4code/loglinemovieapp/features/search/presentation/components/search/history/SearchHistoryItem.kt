package com.patch4code.loglinemovieapp.features.search.presentation.components.search.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.search.domain.model.SearchHistoryItem

@Composable
fun SearchHistoryItem(searchHistoryItem: SearchHistoryItem,onItemClick:()->Unit, onDeleteClick:()->Unit){

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable{ onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ){

        Icon(imageVector = Icons.Default.History, contentDescription = null)
        Spacer(modifier = Modifier.padding(8.dp))

        Text(searchHistoryItem.query, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)

        IconButton(onClick = { onDeleteClick() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }

}