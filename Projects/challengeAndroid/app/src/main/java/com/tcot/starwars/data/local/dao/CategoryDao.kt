package com.tcot.starwars.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tcot.starwars.data.local.CategoryEntity

@Dao
interface CategoryDao {

    @Upsert
    suspend fun upsertAll(category: List<CategoryEntity>)

    @Query("SELECT * FROM categoryentity")
    fun getAll(): List<CategoryEntity>
}