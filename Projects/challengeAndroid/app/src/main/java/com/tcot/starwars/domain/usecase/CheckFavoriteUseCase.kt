package com.tcot.starwars.domain.usecase

import com.tcot.starwars.common.Resource
import com.tcot.starwars.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckFavoriteUseCase
@Inject
constructor(
    private val repository: FavoriteRepository,
) {

    operator fun invoke(name: String): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            emit(Resource.Success(repository.checkFavorite(name)))
        }.catch { e ->
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }.flowOn(Dispatchers.IO)
}