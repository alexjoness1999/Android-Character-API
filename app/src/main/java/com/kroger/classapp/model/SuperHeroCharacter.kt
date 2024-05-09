package com.kroger.classapp.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuperHeroCharacter(
    @field:Json(name = "alias")
    val alias: List<String>,
    @field:Json(name = "birthplace")
    val birthplace: String,
    @field:Json(name = "episodes")
    val episodes: List<String>,
    @field:Json(name = "gender")
    val gender: String,
    @field:Json(name = "height")
    val height: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "img")
    val img: String?,
    @field:Json(name = "name")
    val name: String,
)
