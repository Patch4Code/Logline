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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * DiaryEditReviewSection - Composable function representing a section (clickable OutlinedCard)
 * of the DiaryEditElementView for opening a dialog for editing the review
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditReviewSection(reviewText: String, onEditReviewPressed:()->Unit){

    OutlinedCard(onClick = { onEditReviewPressed() }, modifier = Modifier.padding(8.dp).fillMaxWidth().height(150.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = reviewText.ifEmpty { stringResource(id = R.string.diary_edit_review_section_text) },
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}