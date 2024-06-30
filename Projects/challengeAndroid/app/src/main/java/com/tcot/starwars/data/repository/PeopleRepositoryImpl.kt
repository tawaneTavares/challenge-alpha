package com.tcot.starwars.data.repository

import com.tcot.starwars.data.local.PersonEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.domain.repository.PeopleRepository
import javax.inject.Inject

class PeopleRepositoryImpl
@Inject
constructor(
    private val db: StarWarsDatabase,
) : PeopleRepository {

    override suspend fun getPersonFromDb(personId: Int): PersonEntity =
        db.peopleDao.getPersonById(personId)

    override suspend fun updatePersonFavored(personId: Int) {
        db.peopleDao.updatePersonFavored(personId)
    }
}