package com.tcot.starwars.domain.usecase

import com.tcot.starwars.common.Resource
import com.tcot.starwars.data.local.toPlanet
import com.tcot.starwars.domain.model.Planet
import com.tcot.starwars.domain.repository.PlanetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPlanetFromDbUseCase @Inject
constructor(
    private val repository: PlanetRepository,
) {
    operator fun invoke(planetId: Int): Flow<Resource<Planet>> =
        flow {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getPlanetFromDb(planetId).toPlanet()))
        }.catch { e ->
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }.flowOn(Dispatchers.IO)
}