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

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val superHeroRepository: SuperHeroRepository
) : ViewModel() {

    private val _character = MutableStateFlow<CharacterDetailEvent>(CharacterDetailEvent.Loading)
    val character: StateFlow<CharacterDetailEvent> = _character

    fun fetchCharacterById(id: Int) {
        _character.value = CharacterDetailEvent.Loading
        viewModelScope.launch {
            when (val response = superHeroRepository.getCharacter(id)) {
                is SuperHeroResponse.Success -> {
                    val character = response.characters.firstOrNull { it.id == id }
                    if (character != null) {
                        _character.value = CharacterDetailEvent.Success(character)
                    } else {
                        _character.value = CharacterDetailEvent.Failure
                    }
                }
                SuperHeroResponse.Error -> _character.value = CharacterDetailEvent.Failure
            }
        }
    }

    sealed class CharacterDetailEvent {
        data class Success(val character: SuperHeroCharacter) : CharacterDetailEvent()
        object Failure : CharacterDetailEvent()
        object Loading : CharacterDetailEvent()
    }
}
