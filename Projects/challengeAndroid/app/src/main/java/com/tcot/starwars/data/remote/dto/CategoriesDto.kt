package com.tcot.starwars.data.remote.dto

import com.tcot.starwars.domain.model.Category

private const val PEOPLE = "character"
private const val PLANETS = "planets"

data class CategoriesDto(
    val films: String,
    val people: String,
    val planets: String,
    val species: String,
    val starships: String,
    val vehicles: String,
)

fun CategoriesDto.toCategoryList(): List<Category> =
    listOf(
        Category(type = PEOPLE, url = people),
        Category(type = PLANETS, url = planets),
    )