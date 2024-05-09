package com.kroger.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroger.classapp.data.model.SuperHeroResponse
import com.kroger.classapp.data.repository.SuperHeroRepository
import com.kroger.classapp.model.SuperHeroCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SuperHeroViewModel @Inject constructor(
    private val superHeroRepository: SuperHeroRepository
) : ViewModel(){

    private val _characters = MutableStateFlow<SuperHeroCharacterEvent>(SuperHeroCharacterEvent.Loading)
    val characters: StateFlow<SuperHeroCharacterEvent> = _characters

    fun fillData() = viewModelScope.launch {
        when (val response = superHeroRepository.getCharacters()){
            SuperHeroResponse.Error -> _characters.value = SuperHeroCharacterEvent.Failure
            is SuperHeroResponse.Success -> _characters.value = SuperHeroCharacterEvent.Success(response.characters)
        }
    }

    sealed class SuperHeroCharacterEvent {
        data class Success(val characters: List<SuperHeroCharacter>)
            : SuperHeroCharacterEvent()
        data object Failure : SuperHeroCharacterEvent()
        data object  Loading : SuperHeroCharacterEvent()
    }
}