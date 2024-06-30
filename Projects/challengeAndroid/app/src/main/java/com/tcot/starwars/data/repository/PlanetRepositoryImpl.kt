package com.tcot.starwars.data.repository

import com.tcot.starwars.data.local.PlanetEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.domain.repository.PlanetRepository
import javax.inject.Inject

class PlanetRepositoryImpl
@Inject
constructor(
    private val db: StarWarsDatabase,
) : PlanetRepository {

    override suspend fun getPlanetFromDb(planetId: Int): PlanetEntity = db.planetDao.getPlanetById(planetId)
    override suspend fun updatePlanetFavored(planetId: Int) {
        db.planetDao.updatePlanetFavored(planetId)
    }
}