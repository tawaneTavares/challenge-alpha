package com.tcot.starwars.domain.model

import com.tcot.starwars.BuildConfig
import com.tcot.starwars.data.remote.dto.CategoriesDto

private const val PEOPLE = "character"
private const val STAR_SHIPS = "starships"
private const val CATEGORIES_URL = "categories/"
private const val JPG_URL = ".jpg"

data class Category(
    val type: String,
    val url: String,
)

fun CategoriesDto.toCategoryList(): List<Category> {
    return listOf(
        Category(type = PEOPLE, url = people),
        Category(type = STAR_SHIPS, url = starships),
    )
}

fun Category.getUrlImage(): String? {
    if (type.isEmpty()) return null
    return "${BuildConfig.BASE_IMG_URL}$CATEGORIES_URL$type$JPG_URL"
}
