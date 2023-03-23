package com.example.ktorapi.domain.usecase

import com.example.ktorapi.data.repo.CharacterRepository
import com.example.ktorapi.data.repo.CharacterRepositoryImpl

class GetCharacterUseCase(private val repository: CharacterRepository = CharacterRepositoryImpl()) {

    suspend fun getCharacter(id: Int) = repository.getCharacter(id = id)

}