package com.example.ktorapi.data.remote.models

import com.example.ktorapi.domain.models.CharacterDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(

    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("type") val type: String,
    @SerialName("gender") val gender: String,
    @SerialName("origin") val origin: OriginDto,
    @SerialName("location") val location: LocationDto,
    @SerialName("image") val image: String,
    @SerialName("episode") val episode: List<String>,
    @SerialName("url") val url: String,
    @SerialName("created") val created: String

) {
    // origin
    @Serializable
    data class OriginDto(
        val name: String,
        val url: String

    ) {
        fun toDomain(): CharacterDetails.Origin = CharacterDetails.Origin(name, url)
    }

    // location
    @Serializable
    data class LocationDto(

        val name: String,
        val url: String

    ) {
        fun toDomain(): CharacterDetails.Location = CharacterDetails.Location(name, url)
    }

    fun toDomain() =
        CharacterDetails(
            id,
            name,
            gender,
            species,
            status,
            image,
            location = location.toDomain(),
            origin = origin.toDomain(),
            episode = episode,
        )
}