package com.kroger.classapp.data.repository

import com.kroger.classapp.data.SuperHeroApi
import com.kroger.classapp.data.model.SuperHeroResponse
import javax.inject.Inject


class SuperHeroRepositoryReal @Inject constructor(
    private val superHeroApi: SuperHeroApi,
) : SuperHeroRepository {

    override suspend fun getCharacters(): SuperHeroResponse {
        val result = superHeroApi.getCharacters()
        return if (result.isSuccessful) {
            SuperHeroResponse.Success(result.body()?.characters ?: emptyList())
        } else {
            SuperHeroResponse.Error
        }
    }

    override suspend fun getCharacter(id: Int): SuperHeroResponse {
        val result = superHeroApi.getCharacters()
        return if (result.isSuccessful) {
            val characters = result.body()?.characters
            val character = characters?.find { it.id == id }
            if (character != null) {
                SuperHeroResponse.Success(listOf(character))
            } else {
                SuperHeroResponse.Error
            }
        } else {
            SuperHeroResponse.Error
        }
    }
}
