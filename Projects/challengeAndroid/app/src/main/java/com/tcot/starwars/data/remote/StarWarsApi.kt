package com.tcot.starwars.data.remote

import com.tcot.starwars.data.remote.dto.CategoriesDto
import retrofit2.http.GET

interface StarWarsApi {
    @GET("api/")
    suspend fun getCategories(): CategoriesDto
}
