package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.features.core.presentation.components.load.LoadErrorDisplay
import com.patch4code.loglinemovieapp.features.core.presentation.components.load.LoadingIndicator
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.Provider
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.ProviderForCountryViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreSettings

@Composable
fun DiscoverProviderDialog(
    showDialog: MutableState<Boolean>,
    discoverOptions: MutableState<DiscoverOptions>,
    providerForCountryViewModel: ProviderForCountryViewModel = viewModel()
) {
    if (!showDialog.value) return

    val context = LocalContext.current
    val dataSettingsStore = remember { StoreSettings(context) }
    val watchCountry = dataSettingsStore.getWatchProvidersCountry.collectAsState(initial = "").value

    LaunchedEffect(watchCountry){
        if(!watchCountry.isNullOrEmpty()){
            providerForCountryViewModel.loadMovieProviders(watchCountry)
            discoverOptions.value = discoverOptions.value.copy(watchRegion = watchCountry)
        }
    }

    val isLoading = providerForCountryViewModel.isLoading.observeAsState().value
    val hasError = providerForCountryViewModel.hasLoadError.observeAsState().value
    val movieProvidersForCountry = providerForCountryViewModel.movieProvidersForCountry.observeAsState().value


    Dialog(
        onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = "Select Movie Providers",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                when{
                    isLoading == true -> LoadingIndicator()
                    hasError == true -> LoadErrorDisplay(
                        onReload = { providerForCountryViewModel.loadMovieProviders(watchCountry) }
                    )
                    else ->
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                        ){
                            movieProvidersForCountry?.forEach { movieProvider ->
                                item {
                                    val isSelected = discoverOptions.value.watchProviders.contains(movieProvider.providerId)
                                    ProviderItem(movieProvider, isSelected){
                                        val updatedWatchProviders = discoverOptions.value.watchProviders.toMutableMap()

                                        if(isSelected){
                                            updatedWatchProviders.remove(movieProvider.providerId)
                                        }else{
                                            updatedWatchProviders[movieProvider.providerId] = movieProvider.providerName
                                        }
                                        discoverOptions.value = discoverOptions.value.copy(watchProviders = updatedWatchProviders)

                                    }
                                }
                            }
                        }
                }
                Button(onClick = { showDialog.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun ProviderItem(movieProvider: Provider, isSelected: Boolean, onClick:() -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()
        .clickable(onClick = { onClick() })
    ) {
        Spacer(Modifier.padding(top = 8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.padding(top = 8.dp))
            Card(modifier = Modifier.height(30.dp).width(30.dp),) {
                AsyncImage(
                    model = TmdbCredentials.OTHER_IMAGE_URL + movieProvider.logoPath,
                    modifier = Modifier.fillMaxHeight(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = movieProvider.providerName,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start= 12.dp).weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.alpha(if(isSelected) 1.0F else 0.0F)
            )
        }

        Spacer(Modifier.padding(bottom = 8.dp))
    }
}
