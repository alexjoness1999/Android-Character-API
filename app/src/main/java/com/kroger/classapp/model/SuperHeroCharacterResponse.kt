package com.kroger.classapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuperHeroCharacterResponse(
    @field:Json(name = "info")
    val info: Info,
    @field:Json(name = "results")
    val characters: List<SuperHeroCharacter>
) {
    @JsonClass(generateAdapter = true)
    data class Info(
        @field:Json(name = "count")
        val count: Int,
        @field:Json(name = "next_page")
        val nextPage: String,
        @field:Json(name = "pages")
        val pages: Int,
        @field:Json(name = "prev_page")
        val prevPage: Any?
    )
}