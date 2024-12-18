package com.patch4code.loglinemovieapp.features.core.domain.model

import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverDecade
import java.time.Year

object MovieYears {

    fun getDecades(): List<String> {
        val currentYear = Year.now().value
        val currentDecade = (currentYear / 10) * 10
        val decades = mutableListOf<String>()

        for (year in currentDecade downTo 1880 step 10) {
            decades.add("${year}s")
        }

        return decades
    }
    fun getDiscoverDecades(): List<DiscoverDecade>{
        val currentYear = Year.now().value
        val currentDecade = (currentYear / 10) * 10
        val discoverDecades = mutableListOf<DiscoverDecade>()

        for (year in currentDecade downTo 1880 step 10) {
            discoverDecades.add(
                DiscoverDecade(
                    decade = "${year}s",
                    decadeStartDate = "${year}-01-01",
                    decadeEndDate = "${year+9}-12-31"
                )
            )
        }
        return discoverDecades
    }


    fun getYears(): List<String> {
        val currentYear = Year.now().value
        val years = mutableListOf<String>()

        for(year in currentYear downTo 1880){
            years.add("$year")
        }

        return years
    }
}