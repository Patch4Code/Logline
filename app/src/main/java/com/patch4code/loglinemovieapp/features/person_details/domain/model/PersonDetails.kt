package com.patch4code.loglinemovieapp.features.person_details.domain.model

import com.google.gson.annotations.SerializedName

data class PersonDetails(
    @SerializedName("biography") val biography: String = "N/A",
    @SerializedName("known_for_department") val knownForDepartment: String = "N/A",
    @SerializedName("name") val name: String = "Alan Smithee",
    @SerializedName("profile_path") val profilePath: String = ""
)
