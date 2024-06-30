package com.tcot.starwars.domain.usecase

import com.tcot.starwars.common.Resource
import com.tcot.starwars.data.local.toLastView
import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.domain.repository.LastViewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLastViewsUseCase @Inject
constructor(
    private val repository: LastViewsRepository,
) {

    operator fun invoke(): Flow<Resource<List<LastView>>> =
        flow {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getLastViews().map { it.toLastView() }))
        }.catch { e ->
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }.flowOn(Dispatchers.IO)
}