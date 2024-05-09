package com.kroger.classapp.data.model

import com.kroger.classapp.model.SuperHeroCharacter

sealed class SuperHeroResponse {
    data class Success(val characters: List<SuperHeroCharacter>) : SuperHeroResponse()
    data object Error : SuperHeroResponse()
}