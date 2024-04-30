package com.patch4code.loglinemovieapp.features.person_details.domain.model

import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PersonDetails - Representing details of a person (Cast or Crew-Member).
 *
 * @author Patch4Code
 */
data class PersonDetails(
    @SerializedName("biography") val biography: String = "N/A",
    @SerializedName("known_for_department") val knownForDepartment: String = "N/A",
    @SerializedName("name") val name: String = "Alan Smithee",
    @SerializedName("profile_path") val profilePath: String = ""
)
