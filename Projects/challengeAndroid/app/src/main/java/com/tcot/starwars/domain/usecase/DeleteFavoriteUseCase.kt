package com.tcot.starwars.domain.usecase

import com.tcot.starwars.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject
constructor(
    private val repository: FavoriteRepository,
) {

    suspend operator fun invoke(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(name)
        }
    }
}