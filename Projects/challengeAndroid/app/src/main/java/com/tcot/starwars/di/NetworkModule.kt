package com.tcot.starwars.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcot.starwars.BuildConfig
import com.tcot.starwars.data.local.PeopleDatabase
import com.tcot.starwars.data.local.PersonEntity
import com.tcot.starwars.data.remote.PeopleRemoteMediator
import com.tcot.starwars.data.remote.StarWarsApi
import com.tcot.starwars.data.repository.CategoriesRepositoryImpl
import com.tcot.starwars.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

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
    fun providesCategoryRepository(retrofit: StarWarsApi): CategoriesRepository = CategoriesRepositoryImpl(retrofit)

    @Provides
    @Singleton
    fun providesPeopleRepository(@ApplicationContext context: Context): PeopleDatabase {
        return Room.databaseBuilder(
            context,
            PeopleDatabase::class.java,
            "people.db",
        ).build()
    }

    @Provides
    @Singleton
    fun providePeoplePager(peopleDb: PeopleDatabase, api: StarWarsApi): Pager<Int, PersonEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PeopleRemoteMediator(
                peopleDb = peopleDb,
                api = api,
            ),
            pagingSourceFactory = {
                peopleDb.peopleDao.pagingSource()
            },
        )
    }
}
