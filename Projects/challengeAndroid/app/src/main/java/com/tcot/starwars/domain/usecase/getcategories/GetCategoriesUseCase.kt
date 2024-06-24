package com.tcot.starwars.domain.usecase.getcategories

import com.tcot.starwars.common.Resource
import com.tcot.starwars.data.remote.dto.CategoriesDto
import com.tcot.starwars.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCategoriesUseCase
    @Inject
    constructor(
        private val repository: CategoriesRepository,
    ) {
        operator fun invoke(): Flow<Resource<CategoriesDto>> =
            flow {
                try {
                    emit(Resource.Loading())
                    val categories = repository.getCategories()
                    emit(Resource.Success(categories))
                } catch (e: HttpException) {
                    emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                } catch (e: IOException) {
                    emit(Resource.Error("Couldn't reach server. Check your internet connection."))
                }
            }
    }
