package com.tcot.starwars.domain.repository

import com.tcot.starwars.data.local.LastViewEntity

interface LastViewsRepository {
    suspend fun getLastViews(): List<LastViewEntity>
    suspend fun upsert(lastView: LastViewEntity)
}