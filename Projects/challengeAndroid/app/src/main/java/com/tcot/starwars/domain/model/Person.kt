package com.tcot.starwars.domain.model

import com.tcot.starwars.BuildConfig

private const val PEOPLE_URL = "characters/"
private const val JPG_URL = ".jpg"

data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val skinColor: String,
    val birthYear: String,
    val gender: String,
    val url: String,
    val id: Int,
)

fun Person.getUrlImage(): String? {
    if (url.isEmpty()) return null
    return "${BuildConfig.BASE_IMG_URL}$PEOPLE_URL$id$JPG_URL"
}

fun getEmptyPerson(): Person = Person("", "", "", "", "", "", "", 0)