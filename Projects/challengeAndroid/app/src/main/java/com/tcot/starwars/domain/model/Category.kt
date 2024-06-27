package com.tcot.starwars.domain.model

import com.tcot.starwars.BuildConfig

private const val CATEGORIES_URL = "categories/"
private const val JPG_URL = ".jpg"

data class Category(
    val type: String,
    val url: String,
)

fun Category.getUrlImage(): String? {
    if (type.isEmpty()) return null
    return "${BuildConfig.BASE_IMG_URL}$CATEGORIES_URL$type$JPG_URL"
}
