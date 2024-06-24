package com.tcot.starwars.presentation.categories

import com.tcot.starwars.domain.model.Category

data class CategoriesListState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val error: String = "",
)
