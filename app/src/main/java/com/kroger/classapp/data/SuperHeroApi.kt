package com.kroger.classapp.data

import com.kroger.classapp.model.SuperHeroCharacter
import com.kroger.classapp.model.SuperHeroCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroApi {
    @GET("characters")
    suspend fun getCharacters(): Response<SuperHeroCharacterResponse>

    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<SuperHeroCharacter>

}