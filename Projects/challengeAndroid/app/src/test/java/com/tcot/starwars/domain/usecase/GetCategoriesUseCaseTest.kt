package com.tcot.starwars.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.tcot.starwars.data.remote.dto.CategoriesDto
import com.tcot.starwars.data.remote.dto.toCategoryEntity
import com.tcot.starwars.data.repository.FakeCategoriesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCategoriesUseCaseTest {

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
    private lateinit var fakeRepository: FakeCategoriesRepository

    @Before
    fun setup() {
        fakeRepository = FakeCategoriesRepository()
        getCategoriesUseCase = GetCategoriesUseCase(fakeRepository)

        val categoriesToInsert = CategoriesDto(
            films = "https://swapi.dev/api/people/",
            people = "https://swapi.dev/api/planets/",
            planets = "https://swapi.dev/api/films/",
            species = "https://swapi.dev/api/species/",
            starships = "https://swapi.dev/api/vehicles/",
            vehicles = "https://swapi.dev/api/starships/",
        )

        runBlocking {
            fakeRepository.upsertAll(categoriesToInsert.toCategoryEntity())
        }
    }

    @Test
    fun `Get categories`() = runBlocking {
        val category = getCategoriesUseCase().first()

        assertThat(category.data?.get(0)?.type ?: "").isNotEqualTo(category.data?.get(1)?.type)
    }
}