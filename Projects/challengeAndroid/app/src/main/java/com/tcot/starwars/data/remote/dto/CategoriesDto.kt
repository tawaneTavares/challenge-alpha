package com.tcot.starwars.data.remote.dto

import com.tcot.starwars.common.Constants.TYPE_PEOPLE
import com.tcot.starwars.common.Constants.TYPE_PLANETS
import com.tcot.starwars.data.local.CategoryEntity
import com.tcot.starwars.domain.model.Category

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
        Category(type = TYPE_PEOPLE, url = people),
        Category(type = TYPE_PLANETS, url = planets),
    )

fun CategoriesDto.toCategoryEntity(): List<CategoryEntity> =
    listOf(
        CategoryEntity(type = TYPE_PEOPLE, url = people),
        CategoryEntity(type = TYPE_PLANETS, url = planets),
    )