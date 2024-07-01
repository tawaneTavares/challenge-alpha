package com.tcot.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcot.starwars.domain.model.Favorite

@Entity
data class FavoriteEntity(
    @PrimaryKey
    val name: String,
    val type: String,
    val id: Int,
    val imageUrl: String,
)

fun FavoriteEntity.toFavorite(): Favorite = Favorite(
    name = name,
    type = type,
    id = id,
    imageUrl = imageUrl,
)
