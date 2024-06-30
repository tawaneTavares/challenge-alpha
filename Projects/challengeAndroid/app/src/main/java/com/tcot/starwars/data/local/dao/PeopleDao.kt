package com.tcot.starwars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tcot.starwars.data.local.PersonEntity

@Dao
interface PeopleDao {

    @Upsert
    suspend fun upsertAll(people: List<PersonEntity>)

    @Query("SELECT * FROM personentity")
    fun pagingSource(): PagingSource<Int, PersonEntity>

    @Query("SELECT * FROM personentity WHERE id = :personId")
    fun getPersonById(personId: Int): PersonEntity

    @Query("DELETE FROM personentity")
    suspend fun clearAll()
}