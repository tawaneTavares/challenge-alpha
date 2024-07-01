package com.tcot.starwars.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcot.starwars.domain.model.LastView

@Entity
data class LastViewEntity(
    @PrimaryKey
    val name: String,
    val type: String,
    var timestamp: Long?,
    val id: Int,
    val imageUrl: String,
)

fun LastViewEntity.toLastView(): LastView {
    return LastView(
        name = name,
        type = type,
        id = id,
        imageUrl = imageUrl,
    )
}
