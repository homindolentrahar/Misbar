package com.homindolentrahar.misbar.data.sources.remote.dtos

import com.google.gson.annotations.SerializedName
import com.homindolentrahar.misbar.domain.models.MoviesModel
import com.homindolentrahar.misbar.others.constants.Constants

data class MoviesDtoResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("results")
    val results: List<MoviesDto>? = null
)

data class MoviesDto(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null
)

fun MoviesDto.toModel(): MoviesModel = MoviesModel(
    overview ?: Constants.NO_OVERVIEW,
    releaseDate ?: Constants.NO_RELEASE,
    voteAverage ?: 0.0,
    id,
    title ?: Constants.NO_TITLE,
    posterPath ?: ""
)

fun List<MoviesDto>.toModels(): List<MoviesModel> = this.map { dto -> dto.toModel() }