package com.tcot.starwars.presentation.planets.planetdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Constants.TYPE_PLANETS
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.model.Favorite
import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.domain.model.getEmptyPlanet
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.domain.usecase.AddFavoriteUseCase
import com.tcot.starwars.domain.usecase.CheckFavoriteUseCase
import com.tcot.starwars.domain.usecase.DeleteFavoriteUseCase
import com.tcot.starwars.domain.usecase.GetPlanetFromDbUseCase
import com.tcot.starwars.domain.usecase.UpdateLastViewUseCase
import com.tcot.starwars.presentation.favorites.CheckFavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    private val getPlanetFromDbUseCase: GetPlanetFromDbUseCase,
    private val updateLastViewUseCase: UpdateLastViewUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PlanetDetailState())
    val state: State<PlanetDetailState> = _state

    private val _checkFavorite = mutableStateOf(CheckFavoriteState())
    val checkFavorite: MutableState<CheckFavoriteState> = _checkFavorite

    fun getPlanetDetail(planetId: Int) {
        getPlanetFromDbUseCase(planetId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val planet = result.data ?: getEmptyPlanet()
                    updateLastViewUseCase(
                        LastView(
                            planet.name,
                            TYPE_PLANETS,
                            planetId,
                            planet.getUrlImage() ?: "",
                        ),
                    )
                    checkIsFavorite(planet.name)
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

    private fun checkIsFavorite(name: String) {
        checkFavoriteUseCase(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val isFavorite = result.data ?: false
                    _checkFavorite.value = CheckFavoriteState(
                        hasResponded = true,
                        isFavorite = isFavorite,
                    )
                }

                is Resource.Error -> {
                    _checkFavorite.value = CheckFavoriteState(
                        hasResponded = true,
                        isFavorite = false,
                    )
                }

                is Resource.Loading -> {
                    _checkFavorite.value = CheckFavoriteState(
                        hasResponded = false,
                        isFavorite = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun favoritePlanet() = viewModelScope.launch {
        val planet = state.value.planet
        if (checkFavorite.value.isFavorite) {
            deleteFavoriteUseCase(planet.name)
        } else {
            addFavoriteUseCase(
                Favorite(
                    name = planet.name,
                    type = TYPE_PLANETS,
                    id = planet.id,
                    imageUrl = planet.getUrlImage() ?: "",
                ),
            )
        }
    }
}