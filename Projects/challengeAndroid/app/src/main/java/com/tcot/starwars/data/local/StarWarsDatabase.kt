package com.tcot.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tcot.starwars.data.local.dao.PeopleDao
import com.tcot.starwars.data.local.dao.PlanetDao

@Database(
    entities = [PersonEntity::class, PlanetEntity::class],
    version = 1,
)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract val peopleDao: PeopleDao
    abstract val planetDao: PlanetDao
}