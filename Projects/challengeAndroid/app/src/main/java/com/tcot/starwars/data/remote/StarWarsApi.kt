package com.tcot.starwars.data.remote

import com.tcot.starwars.data.remote.dto.CategoriesDto
import com.tcot.starwars.data.remote.dto.PaginationDto
import com.tcot.starwars.data.remote.dto.PersonDto
import com.tcot.starwars.data.remote.dto.PlanetDto
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {
    @GET("api/")
    suspend fun getCategories(): CategoriesDto

    @GET("api/people/")
    suspend fun getPeopleList(
        @Query("page") page: Int,
    ): PaginationDto<PersonDto>

    @GET("api/planets/")
    suspend fun getPlanetList(
        @Query("page") page: Int,
    ): PaginationDto<PlanetDto>
}
