package com.homindolentrahar.misbar.data.sources.remote.dtos

import com.google.gson.annotations.SerializedName
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.others.constants.Constants

data class SearchDtoResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("results")
    val results: List<SearchDto>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

data class SearchDto(

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    )

fun SearchDto.toModel(): SearchModel = SearchModel(
    mediaType ?: "",
    name ?: Constants.NO_NAME,
    id,
    overview ?: Constants.NO_OVERVIEW,
    title ?: Constants.NO_TITLE,
    posterPath ?: "",
    releaseDate ?: Constants.NO_RELEASE,
    voteAverage ?: 0.0,
    firstAirDate ?: Constants.NO_AIRING,
)

fun List<SearchDto>.toModels(): List<SearchModel> = this.map { dto -> dto.toModel() }
