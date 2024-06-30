package com.tcot.starwars.presentation.lastviews

import com.tcot.starwars.domain.model.LastView

data class LastViewsDetailState(
    val isLoading: Boolean = false,
    val lastViews: List<LastView> = emptyList(),
    val error: String = "",
)