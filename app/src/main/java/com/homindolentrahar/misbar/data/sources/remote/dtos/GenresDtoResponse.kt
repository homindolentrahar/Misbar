package com.homindolentrahar.misbar.data.sources.remote.dtos

import com.google.gson.annotations.SerializedName
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.others.constants.Constants

data class GenresDtoResponse(

    @field:SerializedName("genres")
    val genres: List<GenresDto>? = null
)

data class GenresDto(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

fun List<GenresDto>.toModels(): List<GenresModel> {
    return this.map { it.toModel() }
}

fun GenresDto.toModel(): GenresModel {
    return GenresModel(id ?: 0, name ?: Constants.NO_NAME)
}
