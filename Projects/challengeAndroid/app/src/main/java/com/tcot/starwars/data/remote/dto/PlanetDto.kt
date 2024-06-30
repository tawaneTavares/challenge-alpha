package com.tcot.starwars.data.remote.dto

import com.tcot.starwars.data.local.PlanetEntity

private const val SPLIT_PLANET_URL = "planets/"

data class PlanetDto(
    val climate: String?,
    val created: String?,
    val diameter: String?,
    val edited: String?,
    val films: List<String>?,
    val gravity: String?,
    val name: String?,
    val orbital_period: String?,
    val population: String?,
    val residents: List<String>?,
    val rotation_period: String?,
    val surface_water: String?,
    val terrain: String?,
    val url: String?,
)

fun PlanetDto.toPlanetEntity(): PlanetEntity {
    return PlanetEntity(
        name = name ?: "",
        climate = climate ?: "",
        population = population ?: "",
        terrain = terrain ?: "",
        url = url ?: "",
        id = url?.substringAfter(SPLIT_PLANET_URL)?.substringBefore('/')?.toInt() ?: 0,
        isFavored = false,
    )
}