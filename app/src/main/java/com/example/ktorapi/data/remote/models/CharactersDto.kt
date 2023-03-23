package com.example.ktorapi.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersDto(
    val info: Info,
    val results: List<CharacterDto>
) {
    @Serializable
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        @SerialName("prev") val previous: String?
    )
}



