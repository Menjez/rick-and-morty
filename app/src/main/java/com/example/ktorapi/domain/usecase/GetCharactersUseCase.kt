package com.example.ktorapi.domain.usecase

import com.example.ktorapi.data.repo.CharacterRepository
import com.example.ktorapi.data.repo.CharacterRepositoryImpl

class GetCharactersUseCase(private val repository: CharacterRepository = CharacterRepositoryImpl()) {

    suspend fun getCharacters() = repository.getCharacters()

}