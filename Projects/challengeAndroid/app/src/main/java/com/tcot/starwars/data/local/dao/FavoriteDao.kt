package com.tcot.starwars.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tcot.starwars.data.local.FavoriteEntity

@Dao
interface FavoriteDao {

    @Upsert
    suspend fun upsert(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favoriteEntity")
    fun getAll(): List<FavoriteEntity>

    @Query("SELECT EXISTS(SELECT * FROM favoriteEntity WHERE name = :name)")
    fun checkFavorite(name: String): Boolean

    @Query("DELETE FROM favoriteEntity WHERE name = :name")
    suspend fun deleteFavorite(name: String)
}