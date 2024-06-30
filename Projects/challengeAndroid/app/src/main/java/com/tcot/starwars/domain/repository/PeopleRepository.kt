package com.tcot.starwars.domain.repository

import com.tcot.starwars.data.local.PersonEntity

interface PeopleRepository {
    suspend fun getPersonFromDb(personId: Int): PersonEntity
    suspend fun updatePersonFavored(personId: Int)
}