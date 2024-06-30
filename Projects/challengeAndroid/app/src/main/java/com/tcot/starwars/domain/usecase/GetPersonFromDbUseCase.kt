package com.tcot.starwars.domain.usecase

import com.tcot.starwars.common.Resource
import com.tcot.starwars.data.local.toPerson
import com.tcot.starwars.domain.model.Person
import com.tcot.starwars.domain.repository.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPersonFromDbUseCase @Inject
constructor(
    private val repository: PeopleRepository,
) {
    operator fun invoke(personId: Int): Flow<Resource<Person>> =
        flow {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getPersonFromDb(personId).toPerson()))
        }.catch { e ->
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }.flowOn(Dispatchers.IO)
}