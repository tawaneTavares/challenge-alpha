package com.tcot.starwars.domain.repository

import com.tcot.starwars.data.local.CategoryEntity
import com.tcot.starwars.data.remote.dto.CategoriesDto

interface CategoriesRepository {
    suspend fun getCategories(): CategoriesDto
    suspend fun getCategoriesDb(): List<CategoryEntity>
    suspend fun upsertAll(categories: List<CategoryEntity>)
}
