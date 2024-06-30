package com.tcot.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcot.starwars.domain.model.Person

@Entity
data class PersonEntity(
    val name: String,
    val height: String,
    val mass: String,
    val skinColor: String,
    val url: String,
    @PrimaryKey
    val id: Int,
    val isFavored: Boolean,
)

fun PersonEntity.toPerson(): Person {
    return Person(
        name = name,
        height = height,
        mass = mass,
        skinColor = skinColor,
        url = url,
        id = id,
        isFavored = isFavored,
    )
}
