package com.patch4code.loglinemovieapp.features.my_movies.presentation.components

enum class MyMoviesSortOption(val label: String) {
    ByAddedDesc("Date Added (Newest First)"),
    ByAddedAsc("Date Added (Oldest First)"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
    ByReleaseDateDesc("Release Date (Newest First)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
    ByRatingDesc("Rating (10-1)"),
    ByRatingAsc("Rating (1-10)")
}