package com.homindolentrahar.misbar.data.sources.remote.dtos

import com.google.gson.annotations.SerializedName
import com.homindolentrahar.misbar.domain.models.ShowsModel
import com.homindolentrahar.misbar.others.constants.Constants

data class ShowsDtoResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("results")
    val results: List<ShowsDto>? = null
)

data class ShowsDto(

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,
)

fun ShowsDto.toModel(): ShowsModel = ShowsModel(
    firstAirDate ?: Constants.NO_AIRING,
    overview ?: Constants.NO_OVERVIEW,
    voteAverage ?: 0.0,
    name ?: Constants.NO_NAME,
    id,
    posterPath ?: "",
)

fun List<ShowsDto>.toModels(): List<ShowsModel> = this.map { dto -> dto.toModel() }
