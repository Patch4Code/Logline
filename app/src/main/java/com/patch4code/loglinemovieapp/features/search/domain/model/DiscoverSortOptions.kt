package com.patch4code.loglinemovieapp.features.search.domain.model

enum class DiscoverSortOptions(val queryParam: String, val label: String){
    POPULARITY_DESC("popularity.desc", "Popularity (Most Popular first)"),
    POPULARITY_ASC("popularity.asc", "Popularity (Least Popular first)"),
    VOTE_AVERAGE_DESC("vote_average.desc", "Average Rating (Highest first)"),
    VOTE_AVERAGE_ASC("vote_average.asc", "Average Rating (Lowest first)"),
    RELEASE_DATE_DESC("primary_release_date.desc", "Release Date (Newest first)"),
    RELEASE_DATE_ASC("primary_release_date.asc", "Release Date (Oldest first)"),
    REVENUE_DESC("revenue.desc", "Revenue");
}
