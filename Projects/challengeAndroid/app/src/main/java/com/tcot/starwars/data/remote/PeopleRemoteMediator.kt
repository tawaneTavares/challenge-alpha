package com.tcot.starwars.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tcot.starwars.data.local.PeopleDatabase
import com.tcot.starwars.data.local.PersonEntity
import com.tcot.starwars.data.remote.dto.toPeopleEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PeopleRemoteMediator(
    private val peopleDb: PeopleDatabase,
    private val api: StarWarsApi,
) : RemoteMediator<Int, PersonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PersonEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true,
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val people = api.getPeopleList(
                loadKey,
            )

            peopleDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    peopleDb.peopleDao.clearAll()
                }
                val peopleEntities = people.results.map { it.toPeopleEntity() }
                peopleDb.peopleDao.upsertAll(peopleEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = people.results.size < state.config.pageSize,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}