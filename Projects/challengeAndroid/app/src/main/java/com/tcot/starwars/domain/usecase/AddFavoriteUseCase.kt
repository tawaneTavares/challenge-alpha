package com.tcot.starwars.domain.usecase

import com.tcot.starwars.domain.model.Favorite
import com.tcot.starwars.domain.model.toFavoriteEntity
import com.tcot.starwars.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFavoriteUseCase @Inject
constructor(
    private val repository: FavoriteRepository,
) {

    suspend operator fun invoke(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.upsert(favorite.toFavoriteEntity())
        }
    }
}