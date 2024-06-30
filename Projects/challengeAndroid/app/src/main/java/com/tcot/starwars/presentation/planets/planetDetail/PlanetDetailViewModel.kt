package com.tcot.starwars.presentation.planets.planetDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.model.getEmptyPlanet
import com.tcot.starwars.domain.usecase.GetPlanetFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    private val getPlanetFromDbUseCase: GetPlanetFromDbUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PlanetDetailState())
    val state: State<PlanetDetailState> = _state

    fun getPlanetDetail(planetId: Int) {
        getPlanetFromDbUseCase(planetId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        PlanetDetailState(
                            planet = result.data ?: getEmptyPlanet(),
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

    fun favoritePlanet(planetId: Int) {
        // TODO add function to favorite
    }
}