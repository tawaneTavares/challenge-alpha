package com.tcot.starwars.presentation.planets.planetdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Constants.TYPE_PEOPLE
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.domain.model.getEmptyPlanet
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.domain.usecase.GetPlanetFromDbUseCase
import com.tcot.starwars.domain.usecase.UpdateLastViewUseCase
import com.tcot.starwars.domain.usecase.UpdatePlanetFavoredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    private val getPlanetFromDbUseCase: GetPlanetFromDbUseCase,
    private val updatePlanetFavoredUseCase: UpdatePlanetFavoredUseCase,
    private val updateLastViewUseCase: UpdateLastViewUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PlanetDetailState())
    val state: State<PlanetDetailState> = _state

    fun getPlanetDetail(planetId: Int) {
        getPlanetFromDbUseCase(planetId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val planet = result.data ?: getEmptyPlanet()
                    updateLastViewUseCase(LastView(planet.name, TYPE_PEOPLE, planetId, planet.getUrlImage() ?: ""))
                    _state.value =
                        PlanetDetailState(
                            planet = planet,
                        )
                }

                is Resource.Error -> {
                    _state.value =
                        PlanetDetailState(
                            error = result.message ?: "An unexpected error occurred",
                        )
                }

                is Resource.Loading -> {
                    _state.value = PlanetDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun favoritePlanet(planetId: Int) = viewModelScope.launch {
        updatePlanetFavoredUseCase(planetId)
    }
}