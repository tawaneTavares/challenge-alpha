package com.tcot.starwars.data.repository

import com.tcot.starwars.data.local.LastViewEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.domain.repository.LastViewsRepository
import javax.inject.Inject

class LastViewsRepositoryImpl @Inject
constructor(
    private val db: StarWarsDatabase,
) : LastViewsRepository {

    override suspend fun getLastViews(): List<LastViewEntity> = db.lastViewsDao.getAll()

    override suspend fun upsert(lastView: LastViewEntity) {
        db.lastViewsDao.upsert(lastView)
    }
}