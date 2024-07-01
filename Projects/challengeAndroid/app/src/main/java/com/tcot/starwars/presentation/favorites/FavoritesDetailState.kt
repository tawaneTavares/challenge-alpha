package com.tcot.starwars.presentation.favorites

import com.tcot.starwars.domain.model.Favorite

data class FavoritesDetailState(
    val isLoading: Boolean = false,
    val favorites: List<Favorite> = emptyList(),
    val error: String = "",
)
