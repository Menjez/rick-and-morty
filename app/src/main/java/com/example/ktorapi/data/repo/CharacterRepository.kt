package com.example.ktorapi.data.repo

import com.example.ktorapi.data.remote.api.CharacterApi
import com.example.ktorapi.domain.models.CharacterDetails

interface CharacterRepository{

    suspend fun getCharacters(): List<CharacterDetails>

    suspend fun getCharacter(id: Int): CharacterDetails

}

class CharacterRepositoryImpl: CharacterRepository {

    private val api = CharacterApi.getInstance()

    override suspend fun getCharacters(): List<CharacterDetails> {
        val list = api.getCharacters().results
        return list.map { it.toDomain() }
    }

    override suspend fun getCharacter(id: Int): CharacterDetails {
        val charDetails = api.getCharacter(id)
        return charDetails.toDomain()
    }
}
