package com.tcot.starwars.presentation.planets

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.tcot.starwars.common.Resource
import com.tcot.starwars.data.local.PlanetEntity
import com.tcot.starwars.data.local.toPlanet
import com.tcot.starwars.domain.model.getEmptyPlanet
import com.tcot.starwars.domain.usecase.GetPlanetFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlanetsViewModel @Inject constructor(
    pager: Pager<Int, PlanetEntity>,
    private val getPlanetFromDbUseCase: GetPlanetFromDbUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PlanetDetailState())
    val state: State<PlanetDetailState> = _state

    val planetPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPlanet() }
        }
        .cachedIn(viewModelScope)

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
}