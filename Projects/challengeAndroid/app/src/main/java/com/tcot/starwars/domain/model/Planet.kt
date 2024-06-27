package com.tcot.starwars.domain.model

import com.tcot.starwars.BuildConfig

private const val PLANET_URL = "planets/"
private const val JPG_URL = ".jpg"

data class Planet(
    val name: String,
    val climate: String,
    val population: String,
    val terrain: String,
    val url: String,
    val id: Int,
)

fun Planet.getUrlImage(): String? {
    if (url.isEmpty()) return null
    return "${BuildConfig.BASE_IMG_URL}$PLANET_URL$id$JPG_URL"
}