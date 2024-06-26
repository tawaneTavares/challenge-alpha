package com.tcot.starwars.data.remote.dto

import com.tcot.starwars.data.local.PeopleEntity
import com.tcot.starwars.domain.model.People

data class PaginationDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Any>
)