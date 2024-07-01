package com.tcot.starwars.data.repository

import com.tcot.starwars.data.local.FavoriteEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl
@Inject
constructor(
    private val db: StarWarsDatabase,
) : FavoriteRepository {

    override suspend fun getFavorites(): List<FavoriteEntity> = db.favoriteDao.getAll()

    override suspend fun checkFavorite(name: String): Boolean = db.favoriteDao.checkFavorite(name)

    override suspend fun upsert(favoriteEntity: FavoriteEntity) {
        db.favoriteDao.upsert(favoriteEntity)
    }

    override suspend fun deleteFavorite(name: String) {
        db.favoriteDao.deleteFavorite(name)
    }
}