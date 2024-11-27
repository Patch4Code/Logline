package com.patch4code.loglinemovieapp.features.my_movies.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MyMoviesSortOption - Enum representing sorting options for MyMovies.
 *
 * @author Patch4Code
 */
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