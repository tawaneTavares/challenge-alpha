package com.tcot.starwars.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcot.starwars.data.local.PersonEntity

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(people: List<PersonEntity>)

    @Query("SELECT * FROM personentity")
    fun pagingSource(): PagingSource<Int, PersonEntity>

    @Query("SELECT * FROM personentity WHERE id = :personId")
    fun getPersonById(personId: Int): PersonEntity

    @Query("UPDATE personentity SET isFavored = NOT isFavored WHERE id = :personId")
    fun updatePersonFavored(personId: Int)

    @Query("DELETE FROM personentity")
    suspend fun clearAll()
}