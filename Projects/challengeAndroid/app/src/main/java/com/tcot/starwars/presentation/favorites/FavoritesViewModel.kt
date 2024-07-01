package com.tcot.starwars.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.usecase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FavoritesDetailState())
    val state: State<FavoritesDetailState> = _state

    fun getFavorites() {
        getFavoritesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        FavoritesDetailState(
                            favorites = result.data ?: emptyList(),
                        )
                }

                is Resource.Error -> {
                    _state.value =
                        FavoritesDetailState(
                            error = result.message ?: "An unexpected error occurred",
                        )
                }

                is Resource.Loading -> {
                    _state.value = FavoritesDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}