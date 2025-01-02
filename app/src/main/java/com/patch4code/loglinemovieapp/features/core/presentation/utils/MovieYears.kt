package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverDecade
import java.time.Year

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieYears - Utility object for generating lists of years and decades related to movie data.
 *
 * @author Patch4Code
 */

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