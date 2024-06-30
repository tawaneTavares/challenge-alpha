package com.tcot.starwars.presentation.lastviews

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.usecase.GetLastViewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LastViewsViewModel @Inject constructor(
    private val getLastViewsUseCase: GetLastViewsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LastViewsDetailState())
    val state: State<LastViewsDetailState> = _state

    fun getLastViews() {
        getLastViewsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                        LastViewsDetailState(
                            lastViews = result.data ?: emptyList(),
                        )
                }

                is Resource.Error -> {
                    _state.value =
                        LastViewsDetailState(
                            error = result.message ?: "An unexpected error occurred",
                        )
                }

                is Resource.Loading -> {
                    _state.value = LastViewsDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}