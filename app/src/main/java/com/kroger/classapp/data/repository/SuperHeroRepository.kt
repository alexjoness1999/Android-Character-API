package com.kroger.classapp.data.repository

import com.kroger.classapp.data.model.SuperHeroResponse

interface SuperHeroRepository {
    suspend fun getCharacters(): SuperHeroResponse
    suspend fun getCharacter(id: Int): SuperHeroResponse
}
