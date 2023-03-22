package com.example.ktorapi.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(

    @SerialName("info") val info: Info? = Info(),
    @SerialName("results") val results: ArrayList<CharacterDto> = arrayListOf()

)

@Serializable
data class Info(

    @SerialName("count") val count: Int? = null,
    @SerialName("pages") val pages: Int? = null,
    @SerialName("next") val next: String? = null,
    @SerialName("prev") val prev: String? = null

)

@Serializable
data class OriginDto(

    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null

) {
    fun toDomain(): Origin  = Origin(name ?: "", url ?: "")
}

@Serializable
data class LocationDto(

    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null

){
    fun toDomain(): Location = Location(name ?: "", url ?: "")
}

@Serializable
data class CharacterDto(

    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("species") val species: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("origin") val origin: OriginDto? = OriginDto(),
    @SerialName("location") val location: LocationDto? = LocationDto(),
    @SerialName("image") val image: String? = null,
    @SerialName("episode") val episode: ArrayList<String> = arrayListOf(),
    @SerialName("url") val url: String? = null,
    @SerialName("created") val created: String? = null

) {
    fun toDomain() = Character(id, name ?: "", species ?: "", gender ?: "", image?:"")
    fun detailsDomain() =
        CharacterDetails(id,
            name ?: "",
            gender ?: "",
            species ?: "",
            status ?: "",
            image?: "",
            location = location!!.toDomain(),
            origin = origin!!.toDomain(),
            episode = episode,

        )
}

data class Character(
    val id: Int?,
    val name: String,
    val species: String,
    val gender: String,
    val image: String,
)

data class CharacterDetails(
    val id: Int?,
    val name: String,
    val gender: String,
    val species: String,
    val status: String,
    val image: String,
    val location: Location,
    val origin: Origin,
    val episode: ArrayList<String>,
)

data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)