package com.example.ktorapi.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktorapi.domain.models.CharacterDetails
import com.example.ktorapi.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel(){

    private val useCase = GetCharactersUseCase()

    private val _characters = MutableStateFlow<List<CharacterDetails>>(listOf())
    val character get() = _characters.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)

    init {
        getCharacters()
    }

    private fun getCharacters(){
        viewModelScope.launch {
            try {
                _characters.value = useCase.getCharacters()
            }
            catch (e: Exception){
                _error.value = e.message.toString()
            }
        }
    }
}

