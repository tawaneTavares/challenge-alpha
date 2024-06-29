package com.tcot.starwars.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcot.starwars.BuildConfig
import com.tcot.starwars.data.local.PersonEntity
import com.tcot.starwars.data.local.PlanetEntity
import com.tcot.starwars.data.local.StarWarsDatabase
import com.tcot.starwars.data.remote.PeopleRemoteMediator
import com.tcot.starwars.data.remote.PlanetRemoteMediator
import com.tcot.starwars.data.remote.StarWarsApi
import com.tcot.starwars.data.repository.CategoriesRepositoryImpl
import com.tcot.starwars.data.repository.PlanetRepositoryImpl
import com.tcot.starwars.domain.repository.CategoriesRepository
import com.tcot.starwars.domain.repository.PlanetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val DEFAULT_PAGE_SIZE = 10

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(): StarWarsApi {
        val moshi =
            Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(StarWarsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCategoryRepository(retrofit: StarWarsApi, starWarsDb: StarWarsDatabase): CategoriesRepository = CategoriesRepositoryImpl(retrofit, starWarsDb)

    @Provides
    @Singleton
    fun provideStarWarsDatabase(@ApplicationContext context: Context): StarWarsDatabase {
        return Room.databaseBuilder(
            context,
            StarWarsDatabase::class.java,
            "starwars.db",
        ).build()
    }

    @Provides
    @Singleton
    fun providePeoplePager(starWarsDb: StarWarsDatabase, api: StarWarsApi): Pager<Int, PersonEntity> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            remoteMediator = PeopleRemoteMediator(
                starWarsDb = starWarsDb,
                api = api,
            ),
            pagingSourceFactory = {
                starWarsDb.peopleDao.pagingSource()
            },
        )
    }

    @Provides
    @Singleton
    fun providePlanetPager(starWarsDb: StarWarsDatabase, api: StarWarsApi): Pager<Int, PlanetEntity> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            remoteMediator = PlanetRemoteMediator(
                starWarsDb = starWarsDb,
                api = api,
            ),
            pagingSourceFactory = {
                starWarsDb.planetDao.pagingSource()
            },
        )
    }

    @Provides
    @Singleton
    fun providePlanetDb(starWarsDb: StarWarsDatabase): PlanetRepository = PlanetRepositoryImpl(starWarsDb)
}
