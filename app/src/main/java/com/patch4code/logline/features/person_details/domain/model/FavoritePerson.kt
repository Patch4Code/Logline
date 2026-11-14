package com.patch4code.logline.features.person_details.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_persons")
data class FavoritePerson(
    @PrimaryKey val id: Int,
    val name: String,
    val profilePath: String?,
    val knownForDepartment: String
)
