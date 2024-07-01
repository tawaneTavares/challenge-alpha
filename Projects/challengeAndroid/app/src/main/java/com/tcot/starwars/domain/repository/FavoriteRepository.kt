package com.tcot.starwars.domain.repository

import com.tcot.starwars.data.local.FavoriteEntity

interface FavoriteRepository {
    suspend fun getFavorites(): List<FavoriteEntity>
    suspend fun checkFavorite(name: String): Boolean
    suspend fun upsert(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavorite(name: String)
}