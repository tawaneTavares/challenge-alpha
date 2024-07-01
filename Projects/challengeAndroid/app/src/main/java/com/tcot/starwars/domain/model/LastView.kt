package com.tcot.starwars.domain.model

import com.tcot.starwars.data.local.LastViewEntity

data class LastView(
    val name: String,
    val type: String,
    val id: Int,
    val imageUrl: String,
)

fun LastView.toLastViewEntity(): LastViewEntity = LastViewEntity(
    name = name,
    type = type,
    timestamp = null,
    id = id,
    imageUrl = imageUrl,
)
