package com.tcot.starwars.presentation.planets

import com.tcot.starwars.domain.model.Planet
import com.tcot.starwars.domain.model.getEmptyPlanet

data class PlanetDetailState(
    val isLoading: Boolean = false,
    val planet: Planet = getEmptyPlanet(),
    val error: String = "",
)