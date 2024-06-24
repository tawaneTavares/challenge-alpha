package com.tcot.starwars.data.repository

import com.tcot.starwars.data.remote.StarWarsApi
import com.tcot.starwars.data.remote.dto.CategoriesDto
import com.tcot.starwars.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl
    @Inject
    constructor(
        private val api: StarWarsApi,
    ) : CategoriesRepository {
        override suspend fun getCategories(): CategoriesDto {
            return api.getCategories()
        }
    }
