package com.kroger.classapp.di

import com.kroger.classapp.data.SuperHeroApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSuperHeroApi(): SuperHeroApi =
        Retrofit.Builder()
            .baseUrl("https://api.attackontitanapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
}