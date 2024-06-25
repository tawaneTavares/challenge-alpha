package com.tcot.starwars.presentation.categories

import com.tcot.starwars.domain.model.Category

data class CategoriesListState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String = "",
)
