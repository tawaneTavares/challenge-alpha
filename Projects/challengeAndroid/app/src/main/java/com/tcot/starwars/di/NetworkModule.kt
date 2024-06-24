package com.tcot.starwars.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcot.starwars.BuildConfig
import com.tcot.starwars.data.remote.StarWarsApi
import com.tcot.starwars.data.repository.CategoriesRepositoryImpl
import com.tcot.starwars.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(): StarWarsApi {
        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(StarWarsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCategoryRepository(retrofit: StarWarsApi): CategoriesRepository {
        return CategoriesRepositoryImpl(retrofit)
    }
}
