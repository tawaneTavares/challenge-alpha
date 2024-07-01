package com.tcot.starwars.data.remote.dto

import com.tcot.starwars.data.local.PersonEntity

private const val SPLIT_PEOPLE_URL = "people/"

data class PersonDto(
    val birth_year: String?,
    val created: String?,
    val edited: String?,
    val eye_color: String?,
    val films: List<String>?,
    val gender: String?,
    val hair_color: String?,
    val height: String?,
    val homeworld: String?,
    val mass: String?,
    val name: String?,
    val skin_color: String?,
    val species: List<String>?,
    val starships: List<String>?,
    val url: String?,
    val vehicles: List<String>?,
)

fun PersonDto.toPeopleEntity(): PersonEntity {
    return PersonEntity(
        name = name ?: "",
        height = height ?: "",
        mass = mass ?: "",
        skinColor = skin_color ?: "",
        birthYear = birth_year ?: "",
        gender = gender ?: "",
        url = url ?: "",
        id = url?.substringAfter(SPLIT_PEOPLE_URL)?.substringBefore('/')?.toInt() ?: 0,
    )
}