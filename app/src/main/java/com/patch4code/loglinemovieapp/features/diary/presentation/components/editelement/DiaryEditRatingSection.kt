package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryEditRatingSection - Composable function representing a section (button) of
 * the DiaryEditElementView for opening a dialog for editing the rating
 *
 * @author Patch4Code
 */
@Composable
fun DiaryEditRatingSection(rating: Int, onButtonPressed: () -> Unit){
    TextButton(onClick = { onButtonPressed() }) {
        Row (modifier = Modifier.weight(1f)){

            if(rating > 0){
                Text(text = "${stringResource(id = R.string.diary_edit_rating_section_rating_text)} $rating" , style = MaterialTheme.typography.titleMedium)
                Icon(imageVector = Icons.Default.StarRate, contentDescription = null)
            }else{
                Text(text = stringResource(id = R.string.diary_edit_rating_section_no_rating_text) , style = MaterialTheme.typography.titleMedium)
            }
        }
        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
    }
}