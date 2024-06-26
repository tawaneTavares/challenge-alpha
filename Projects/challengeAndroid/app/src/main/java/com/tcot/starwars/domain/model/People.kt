package com.tcot.starwars.domain.model

import com.tcot.starwars.BuildConfig

private const val PEOPLE_URL = "characters/"
private const val JPG_URL = ".jpg"

data class People(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val homeWorld: String,
    val url: String,
    val id: Int
)

fun People.getUrlImage(): String? {
    if (url.isEmpty()) return null
    return "${BuildConfig.BASE_IMG_URL}$PEOPLE_URL$id$JPG_URL"
}