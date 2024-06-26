package com.tcot.starwars.data.remote.dto

data class PaginationDto<out T : Any>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
)