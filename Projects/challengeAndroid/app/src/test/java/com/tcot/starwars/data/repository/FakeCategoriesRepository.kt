package com.tcot.starwars.data.repository

import com.tcot.starwars.data.local.CategoryEntity
import com.tcot.starwars.data.remote.dto.CategoriesDto
import com.tcot.starwars.domain.repository.CategoriesRepository

class FakeCategoriesRepository : CategoriesRepository {

    private val categoriesList = mutableListOf<CategoryEntity>()

    override suspend fun getCategories(): CategoriesDto {
        return CategoriesDto(
            categoriesList[0].url,
            categoriesList[1].url,
            "",
            "",
            "",
            "",
        )
    }

    override suspend fun getCategoriesDb(): List<CategoryEntity> {
        return categoriesList
    }

    override suspend fun upsertAll(categories: List<CategoryEntity>) {
        categoriesList.clear()
        categoriesList.addAll(categories)
    }
}