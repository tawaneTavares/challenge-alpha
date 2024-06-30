package com.tcot.starwars.domain.usecase

import com.tcot.starwars.domain.repository.PeopleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdatePersonFavoredUseCase @Inject
constructor(
    private val repository: PeopleRepository,
) {

    suspend operator fun invoke(personId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updatePersonFavored(personId)
        }
    }
}