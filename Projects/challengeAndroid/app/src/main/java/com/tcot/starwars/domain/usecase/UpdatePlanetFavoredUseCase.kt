package com.tcot.starwars.domain.usecase

import com.tcot.starwars.domain.repository.PlanetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdatePlanetFavoredUseCase @Inject
constructor(
    private val repository: PlanetRepository,
) {

    suspend operator fun invoke(planetId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updatePlanetFavored(planetId)
        }
    }
}