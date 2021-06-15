package com.homindolentrahar.misbar.data.sources.remote.dtos

import com.google.gson.annotations.SerializedName
import com.homindolentrahar.misbar.domain.models.*
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.others.constants.Constants

data class DetailShowsDto(

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("seasons")
    val seasons: List<ShowsSeasonsDto>? = null,

    @field:SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @field:SerializedName("created_by")
    val createdBy: List<ShowsCreatedByDto>? = null,

    @field:SerializedName("last_episode_to_air")
    val lastEpisodeToAir: ShowsLastEpisodeToAirDto? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("spoken_languages")
    val spokenLanguages: List<ShowsSpokenLanguagesDto>? = null,

    @field:SerializedName("production_companies")
    val productionCompanies: List<ShowsProductionCompaniesDto>? = null,

    @field:SerializedName("genres")
    val genres: List<ShowsGenresDto?>? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("tagline")
    val tagline: String? = null,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,

    @field:SerializedName("next_episode_to_air")
    val nextEpisodeToAir: ShowsNextEpisodeToAirDto? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null
)

fun DetailShowsDto.toModel(): DetailShowsModel =
    DetailShowsModel(
        firstAirDate ?: Constants.NO_AIRING,
        overview ?: Constants.NO_OVERVIEW,
        seasons?.map { it.toModel() } ?: listOf(),
        numberOfEpisodes ?: 0,
        createdBy?.map { it.toModel() } ?: listOf(),
        lastEpisodeToAir?.toModel() ?: ShowsLastEpisodeToAirModel.empty(),
        posterPath ?: "",
        backdropPath ?: "",
        spokenLanguages?.map { it.toModel() } ?: listOf(),
        productionCompanies?.map { it.toModel() } ?: listOf(),
        genres?.map { it!!.toModel() } ?: listOf(),
        originalName ?: Constants.NO_NAME,
        voteAverage ?: 0.0,
        name ?: Constants.NO_NAME,
        tagline ?: "",
        episodeRunTime ?: listOf(),
        id ?: 0,
        numberOfSeasons ?: 0,
        nextEpisodeToAir?.toModel() ?: ShowsNextEpisodeToAirModel.empty(),
        voteCount ?: 0,
    )

data class ShowsSpokenLanguagesDto(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null,

    @field:SerializedName("english_name")
    val englishName: String? = null
)

fun ShowsSpokenLanguagesDto.toModel(): String = englishName ?: Constants.NO_NAME

data class ShowsSeasonsDto(

    @field:SerializedName("air_date")
    val airDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("episode_count")
    val episodeCount: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("season_number")
    val seasonNumber: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null
)

fun ShowsSeasonsDto.toModel(): ShowsSeasonsModel = ShowsSeasonsModel(
    airDate ?: Constants.NO_AIRING,
    overview ?: Constants.NO_OVERVIEW,
    episodeCount ?: 0,
    name ?: Constants.NO_NAME,
    seasonNumber ?: 0,
    posterPath ?: "",
    id ?: 0
)

data class ShowsNextEpisodeToAirDto(

    @field:SerializedName("air_date")
    val airDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("episode_number")
    val episodeNumber: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("season_number")
    val seasonNumber: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("still_path")
    val stillPath: String? = null
)

fun ShowsNextEpisodeToAirDto.toModel(): ShowsNextEpisodeToAirModel = ShowsNextEpisodeToAirModel(
    airDate ?: Constants.NO_AIRING,
    overview ?: Constants.NO_OVERVIEW,
    episodeNumber ?: 0,
    name ?: Constants.NO_NAME,
    seasonNumber ?: 0,
    stillPath ?: "",
    id ?: 0
)

data class ShowsCreatedByDto(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

fun ShowsCreatedByDto.toModel(): ShowsCreatedByModel = ShowsCreatedByModel(
    name ?: Constants.NO_NAME,
    profilePath ?: "",
    id ?: 0
)

data class ShowsLastEpisodeToAirDto(

    @field:SerializedName("air_date")
    val airDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("episode_number")
    val episodeNumber: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("season_number")
    val seasonNumber: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("still_path")
    val stillPath: String? = null
)

fun ShowsLastEpisodeToAirDto.toModel(): ShowsLastEpisodeToAirModel =
    ShowsLastEpisodeToAirModel(
        airDate ?: Constants.NO_AIRING,
        overview ?: Constants.NO_OVERVIEW,
        episodeNumber ?: 0,
        name ?: Constants.NO_NAME,
        seasonNumber ?: 0,
        stillPath ?: "",
        id ?: 0
    )

data class ShowsGenresDto(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

fun ShowsGenresDto.toModel(): GenresModel = GenresModel(
    id ?: 0,
    name ?: Constants.NO_NAME
)

data class ShowsProductionCompaniesDto(

    @field:SerializedName("logo_path")
    val logoPath: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("origin_country")
    val originCountry: String? = null
)

fun ShowsProductionCompaniesDto.toModel(): ShowsProductionCompaniesModel =
    ShowsProductionCompaniesModel(
        logoPath ?: "",
        name ?: Constants.NO_NAME,
        id ?: 0,
        originCountry ?: Constants.NO_COUNTRY
    )
