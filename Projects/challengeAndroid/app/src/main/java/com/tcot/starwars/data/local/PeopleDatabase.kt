package com.tcot.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tcot.starwars.data.local.dao.PeopleDao

@Database(
    entities = [PeopleEntity::class],
    version = 1,
)
abstract class PeopleDatabase : RoomDatabase() {
    abstract val peopleDao: PeopleDao
}