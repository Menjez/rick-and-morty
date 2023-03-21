package com.example.ktorapi.data.repo

import com.example.ktorapi.data.api.CharacterApi
import com.example.ktorapi.data.dtos.Character
import com.example.ktorapi.data.dtos.CharacterDetails

interface CharacterRepository{
    suspend fun getCharacter(): List<Character>

    suspend fun getCharacterDetails(id: Int): CharacterDetails

}

class CharacterRepositoryImpl: CharacterRepository {

    private val api = CharacterApi.getInstance()

    override suspend fun getCharacter(): List<Character> {
        val list = api.getCharacter().results
        return list.map { it.toDomain() }
    }

    override suspend fun getCharacterDetails(id: Int): CharacterDetails {
        val charDetails = api.getCharacterDetails(id)
        return charDetails.detailsDomain()
    }

}
