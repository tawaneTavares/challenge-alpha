package com.tcot.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcot.starwars.domain.model.Planet

@Entity
data class PlanetEntity(
    val name: String,
    val climate: String,
    val population: String,
    val terrain: String,
    val url: String,
    @PrimaryKey
    val id: Int,
)

fun PlanetEntity.toPlanet(): Planet {
    return Planet(
        name = name,
        climate = climate,
        population = population,
        terrain = terrain,
        url = url,
        id = id,
    )
}