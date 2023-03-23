package com.example.ktorapi.domain.models

data class CharacterDetails(
    val id: Int,
    val name: String,
    val gender: String,
    val species: String,
    val status: String,
    val image: String,
    val location: Location,
    val origin: Origin,
    val episode: List<String>,
){
    data class Origin(
        val name: String,
        val url: String
    )

    data class Location(
        val name: String,
        val url: String
    )
}