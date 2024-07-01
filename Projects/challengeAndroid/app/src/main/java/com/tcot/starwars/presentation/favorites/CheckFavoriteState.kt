package com.tcot.starwars.presentation.favorites

data class CheckFavoriteState(
    val hasResponded: Boolean = false,
    val isFavorite: Boolean = false,
)
