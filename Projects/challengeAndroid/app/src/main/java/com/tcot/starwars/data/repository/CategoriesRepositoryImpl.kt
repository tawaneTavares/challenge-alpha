package com.tcot.starwars.data.repository

import com.tcot.starwars.data.local.CategoryEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.data.remote.StarWarsApi
import com.tcot.starwars.data.remote.dto.CategoriesDto
import com.tcot.starwars.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl
@Inject
constructor(
    private val api: StarWarsApi,
    private val db: StarWarsDatabase,
) : CategoriesRepository {
    override suspend fun getCategories(): CategoriesDto = api.getCategories()
    override suspend fun getCategoriesDb(): List<CategoryEntity> = db.categoryDao.getAll()
    override suspend fun upsertAll(categories: List<CategoryEntity>) {
        db.categoryDao.upsertAll(categories)
    }
}
