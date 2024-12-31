@file:Suppress("UNCHECKED_CAST")

package com.patch4code.loglinemovieapp.features.search.presentation.components.utils

import androidx.compose.runtime.saveable.Saver
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

object DiscoverOptionsSaver {
    val saver = Saver<DiscoverOptions, Map<String, Any?>>(
        save = {discoverOptions->
            mapOf(
                "sortBy" to discoverOptions.sortBy,
                "genres" to discoverOptions.genres,
                "primaryReleaseYear" to discoverOptions.primaryReleaseYear,
                "primaryReleaseDateGte" to discoverOptions.primaryReleaseDateGte,
                "primaryReleaseDateLte" to discoverOptions.primaryReleaseDateLte,
                "originCountry" to discoverOptions.originCountry,
                "originalLanguage" to discoverOptions.originalLanguage,
                "watchRegion" to discoverOptions.watchRegion,
                "watchProviders" to discoverOptions.watchProviders,
                "withKeywords" to discoverOptions.withKeywords,
                "withoutKeywords" to discoverOptions.withoutKeywords,
                "voteAverageGte" to discoverOptions.voteAverageGte,
                "voteAverageLte" to discoverOptions.voteAverageLte,
                "voteCountGte" to discoverOptions.voteCountGte,
                "people" to discoverOptions.people,
                "companies" to discoverOptions.companies
            )
        },
        restore = {savedMap->
            DiscoverOptions(
                sortBy = savedMap["sortBy"] as String,
                genres = savedMap["genres"] as List<Int>,
                primaryReleaseYear = savedMap["primaryReleaseYear"] as Int?,
                primaryReleaseDateGte = savedMap["primaryReleaseDateGte"] as String?,
                primaryReleaseDateLte = savedMap["primaryReleaseDateLte"] as String?,
                originCountry = savedMap["originCountry"] as String?,
                originalLanguage = savedMap["originalLanguage"] as String?,
                watchRegion = savedMap["watchRegion"] as String?,
                watchProviders = savedMap["watchProviders"] as Map<Int, String>,
                withKeywords = savedMap["withKeywords"] as String?,
                withoutKeywords = savedMap["withoutKeywords"] as String?,
                voteAverageGte = savedMap["voteAverageGte"] as Float?,
                voteAverageLte = savedMap["voteAverageLte"] as Float?,
                voteCountGte = savedMap["voteCountGte"] as Float?,
                people = savedMap["people"] as String?,
                companies = savedMap["companies"] as String?
            )
        }
    )
}