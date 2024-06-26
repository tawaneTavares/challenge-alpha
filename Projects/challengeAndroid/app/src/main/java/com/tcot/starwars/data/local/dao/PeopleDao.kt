package com.tcot.starwars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tcot.starwars.data.local.PeopleEntity

@Dao
interface PeopleDao {

    @Upsert
    suspend fun upsertAll(peoples: List<PeopleEntity>)

    @Query("SELECT * FROM peopleentity")
    fun pagingSource(): PagingSource<Int, PeopleEntity>

    @Query("DELETE FROM peopleentity")
    suspend fun clearAll()
}