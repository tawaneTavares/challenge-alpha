package com.tcot.starwars.domain.usecase.getcategories

import com.tcot.starwars.common.Resource
import com.tcot.starwars.data.local.toCategory
import com.tcot.starwars.data.remote.dto.toCategoryEntity
import com.tcot.starwars.data.remote.dto.toCategoryList
import com.tcot.starwars.domain.model.Category
import com.tcot.starwars.domain.repository.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCategoriesUseCase
@Inject
constructor(
    private val repository: CategoriesRepository,
) {
    operator fun invoke(): Flow<Resource<List<Category>>> =
        flow {
            try {
                emit(Resource.Loading())

                val categoriesDb = repository.getCategoriesDb()

                if (categoriesDb.isNotEmpty()) {
                    emit(Resource.Success(categoriesDb.map { it.toCategory() }))
                } else {
                    val categories = repository.getCategories()
                    repository.upsertAll(categories.toCategoryEntity())
                    emit(Resource.Success(categories.toCategoryList()))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }.flowOn(Dispatchers.IO)
}
