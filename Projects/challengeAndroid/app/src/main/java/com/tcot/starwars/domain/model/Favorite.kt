package com.tcot.starwars.domain.model

import com.tcot.starwars.data.local.FavoriteEntity

data class Favorite(
    val name: String,
    val type: String,
    val id: Int,
    val imageUrl: String,
)

fun Favorite.toFavoriteEntity(): FavoriteEntity = FavoriteEntity(
    name = name,
    type = type,
    id = id,
    imageUrl = imageUrl,
)