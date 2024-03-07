package com.moritz.movieappuitest.features.reviews.presentation.screen_reviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.moritz.movieappuitest.features.reviews.presentation.components.ReviewDetailsInfo
import com.moritz.movieappuitest.features.reviews.presentation.components.ReviewDetailsPoster

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewDetailsView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    loggedElementId: String?,
    reviewDetailsViewModel: ReviewDetailsViewModel = viewModel()){

    //val decodedLoggedElementString = URLDecoder.decode(loggedElement, "UTF-8")
    //val loggedElementData: LoggedMovie = JSONHelper.fromJson(decodedLoggedElementString)

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewDetailScreen)
        loggedElementId?.let { reviewDetailsViewModel.setCurrentReviewedLog(it) }
    }

    val reviewedLog = reviewDetailsViewModel.currentReviewedLog.observeAsState().value



    val review: String = reviewedLog?.review ?: ""

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                if (reviewedLog != null) {
                    ReviewDetailsPoster(
                        loggedElementData = reviewedLog,
                        onPosterPressed = {navController.navigate(Screen.MovieScreen.withArgs(
                            reviewedLog.movie.id.toString()))
                        }
                    )
                }

                if (reviewedLog != null) {
                    ReviewDetailsInfo(
                        loggedElementData = reviewedLog,
                        onEditPressed = {encodedJsonLoggedItem->
                            navController.navigate(Screen.DiaryEditElementScreen.withArgs(encodedJsonLoggedItem))
                        }
                    )
                }
            }
            //Review Text
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = review, style = MaterialTheme.typography.bodyMedium)
        }
    }
}