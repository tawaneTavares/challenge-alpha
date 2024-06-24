package com.tcot.starwars.domain.repository

import com.tcot.starwars.data.remote.dto.CategoriesDto

interface CategoriesRepository {
    suspend fun getCategories(): CategoriesDto
}
