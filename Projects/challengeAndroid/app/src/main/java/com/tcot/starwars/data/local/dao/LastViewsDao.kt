package com.tcot.starwars.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tcot.starwars.data.local.LastViewEntity

@Dao
interface LastViewsDao {

    @Upsert
    suspend fun upsert(lastViewsEntity: LastViewEntity)

    @Query("SELECT * FROM lastViewEntity ORDER BY timestamp DESC")
    fun getAll(): List<LastViewEntity>
}