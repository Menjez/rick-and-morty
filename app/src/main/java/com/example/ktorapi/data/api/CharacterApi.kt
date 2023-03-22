package com.example.ktorapi.data.api

import com.example.ktorapi.data.dtos.CharacterDto
import com.example.ktorapi.data.dtos.CharactersResponse
import com.example.ktorapi.data.ktorHttpClient
import io.ktor.client.*
import io.ktor.client.request.*

class CharacterApi private constructor(private val client: HttpClient) {

    companion object {
        fun getInstance() = CharacterApi(client = ktorHttpClient)
    }

    private val base = "https://rickandmortyapi.com/api/"

    suspend fun getCharacter(): CharactersResponse {
        return client.get(base.plus("character"))
    }

    suspend fun getCharacterDetails(id: Int) : CharacterDto {
        return client.get(base.plus("character/$id"))
    }
}

