package com.homindolentrahar.misbar.data.sources.remote.dtos

import com.google.gson.annotations.SerializedName
import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
import com.homindolentrahar.misbar.domain.models.MoviesProductionCompaniesModel
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.others.constants.Constants

data class DetailMoviesDto(
    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("runtime")
    val runtime: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("spoken_languages")
    val spokenLanguages: List<MoviesSpokenLanguagesDto>? = null,

    @field:SerializedName("revenue")
    val revenue: Int? = null,

    @field:SerializedName("production_companies")
    val productionCompanies: List<MoviesProductionCompaniesDto>? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("genres")
    val genres: List<MoviesGenresDto>? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("production_countries")
    val productionCountries: List<MoviesProductionCountriesDto>? = null,

    @field:SerializedName("tagline")
    val tagline: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null,

    @field:SerializedName("budget")
    val budget: Int? = null
)

fun DetailMoviesDto.toModel(): DetailMoviesModel =
    DetailMoviesModel(
        overview ?: Constants.NO_OVERVIEW,
        originalTitle ?: Constants.NO_TITLE,
        runtime ?: 0,
        title ?: Constants.NO_TITLE,
        posterPath ?: "",
        backdropPath ?: "",
        spokenLanguages?.map { it.toModel() } ?: listOf(),
        revenue ?: 0,
        productionCompanies?.map { it.toModel() } ?: listOf(),
        releaseDate ?: Constants.NO_RELEASE,
        genres?.map { it.toModel() } ?: listOf(),
        voteAverage ?: 0.0,
        productionCountries?.map { it.toModel() } ?: listOf(),
        tagline ?: "",
        id ?: 0,
        voteCount ?: 0,
        budget ?: 0
    )

data class MoviesSpokenLanguagesDto(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null,

    @field:SerializedName("english_name")
    val englishName: String? = null
)

fun MoviesSpokenLanguagesDto.toModel(): String = englishName ?: Constants.NO_NAME

data class MoviesProductionCountriesDto(

    @field:SerializedName("name")
    val name: String? = null
)

fun MoviesProductionCountriesDto.toModel(): String = name ?: Constants.NO_NAME

data class MoviesGenresDto(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

fun MoviesGenresDto.toModel(): GenresModel = GenresModel(
    id ?: 0,
    name ?: Constants.NO_NAME
)

data class MoviesProductionCompaniesDto(

    @field:SerializedName("logo_path")
    val logoPath: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("origin_country")
    val originCountry: String? = null
)

fun MoviesProductionCompaniesDto.toModel(): MoviesProductionCompaniesModel =
    MoviesProductionCompaniesModel(
        name ?: Constants.NO_NAME,
        id ?: 0,
    )
