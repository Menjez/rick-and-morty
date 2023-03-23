package com.example.ktorapi.data.remote.api

import com.example.ktorapi.data.remote.models.CharacterDto
import com.example.ktorapi.data.remote.models.CharactersDto
import io.ktor.client.*
import io.ktor.client.request.*

class CharacterApi private constructor(private val client: HttpClient) {

    companion object {
        fun getInstance() = CharacterApi(client = ktorHttpClient)
    }

    private val base = "https://rickandmortyapi.com/api/"

    suspend fun getCharacters(): CharactersDto = client.get(base.plus("character"))

    suspend fun getCharacter(id: Int): CharacterDto = client.get(base.plus("character/$id"))

}

