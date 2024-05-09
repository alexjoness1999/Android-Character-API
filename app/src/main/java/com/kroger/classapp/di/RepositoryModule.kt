package com.kroger.classapp.di

import com.kroger.classapp.data.repository.SuperHeroRepository
import com.kroger.classapp.data.repository.SuperHeroRepositoryReal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSuperHeroRepository(
        superHeroRepositoryReal: SuperHeroRepositoryReal,
    ): SuperHeroRepository
}