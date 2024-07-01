package com.tcot.starwars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcot.starwars.data.local.PlanetEntity

@Dao
interface PlanetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(people: List<PlanetEntity>)

    @Query("SELECT * FROM planetentity")
    fun pagingSource(): PagingSource<Int, PlanetEntity>

    @Query("SELECT * FROM planetentity WHERE id = :planetId")
    fun getPlanetById(planetId: Int): PlanetEntity

    @Query("DELETE FROM planetentity")
    suspend fun clearAll()
}