package com.tcot.starwars.domain.usecase

import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.domain.model.toLastViewEntity
import com.tcot.starwars.domain.repository.LastViewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateLastViewUseCase @Inject
constructor(
    private val repository: LastViewsRepository,
) {

    suspend operator fun invoke(lastView: LastView) {
        CoroutineScope(Dispatchers.IO).launch {
            val lastViewsEntity = lastView.toLastViewEntity()
            lastViewsEntity.timestamp = System.currentTimeMillis()
            repository.upsert(lastViewsEntity)
        }
    }
}