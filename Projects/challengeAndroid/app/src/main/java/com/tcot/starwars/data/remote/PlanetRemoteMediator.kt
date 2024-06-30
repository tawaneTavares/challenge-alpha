package com.tcot.starwars.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tcot.starwars.data.local.PlanetEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.data.remote.dto.toPlanetEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PlanetRemoteMediator(
    private val starWarsDb: StarWarsDatabase,
    private val api: StarWarsApi,
) : RemoteMediator<Int, PlanetEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlanetEntity>,
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

            val planet = api.getPlanetList(
                loadKey,
            )

            starWarsDb.withTransaction {
                val planetEntities = planet.results.map { it.toPlanetEntity() }
                starWarsDb.planetDao.insertAll(planetEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = planet.results.size < state.config.pageSize,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}