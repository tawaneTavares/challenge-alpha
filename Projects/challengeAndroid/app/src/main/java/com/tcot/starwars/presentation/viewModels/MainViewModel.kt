package com.tcot.starwars.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcot.starwars.domain.usecase.getcategories.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val getCategoriesUseCase: GetCategoriesUseCase) :
    ViewModel() {
        private val _isReady = MutableStateFlow(false)
        val isReady = _isReady.asStateFlow()

        init {
            viewModelScope.launch {
                // TODO: testing splash, add code later to wait for response and remove delay fixed time
                delay(3000L)
                _isReady.value = true
            }
        }
    }
