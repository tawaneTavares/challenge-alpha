package com.tcot.starwars.domain.repository

import com.tcot.starwars.data.local.PlanetEntity

interface PlanetRepository {
    suspend fun getPlanetFromDb(planetId: Int): PlanetEntity
    suspend fun updatePlanetFavored(planetId: Int)
}