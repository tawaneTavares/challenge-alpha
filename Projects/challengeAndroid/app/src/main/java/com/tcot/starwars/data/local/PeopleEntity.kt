package com.tcot.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcot.starwars.domain.model.People

@Entity
data class PeopleEntity(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val homeWorld: String,
    val url: String,
    @PrimaryKey
    val id: Int
)

fun PeopleEntity.toPeople(): People {
    return People(
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        homeWorld = homeWorld,
        url = url,
        id = id
    )
}
