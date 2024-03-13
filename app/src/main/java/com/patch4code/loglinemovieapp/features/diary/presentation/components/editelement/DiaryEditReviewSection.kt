package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DiaryEditReviewSection(reviewText: String, onEditReviewPressed:()->Unit){

    OutlinedCard(onClick = { onEditReviewPressed() }, modifier = Modifier.padding(8.dp).fillMaxWidth().height(150.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = reviewText.ifEmpty { "Add review ..." },
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}