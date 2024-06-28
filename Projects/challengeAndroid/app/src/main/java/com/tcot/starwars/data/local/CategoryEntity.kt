package com.tcot.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcot.starwars.domain.model.Category

@Entity
data class CategoryEntity(
    @PrimaryKey
    val type: String,
    val url: String,
)

fun CategoryEntity.toCategory(): Category {
    return Category(
        type = type,
        url = url,
    )
}