package com.patch4code.loglinemovieapp.features.core.domain.model

object MovieYears {

    fun getDecades(): List<String> {
        val currentYear = java.time.Year.now().value
        val currentDecade = (currentYear / 10) * 10
        val decades = mutableListOf<String>()

        for (year in currentDecade downTo 1880 step 10) {
            decades.add("${year}s")
        }

        return decades
    }

    fun getYears(): List<String> {
        val currentYear = java.time.Year.now().value
        val years = mutableListOf<String>()

        for(year in currentYear downTo 1880){
            years.add("$year")
        }

        return years
    }
}