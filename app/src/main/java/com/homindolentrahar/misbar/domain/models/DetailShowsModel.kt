package com.homindolentrahar.misbar.domain.models

import com.homindolentrahar.misbar.others.constants.Constants

data class DetailShowsModel(
    val firstAirDate: String,
    val overview: String,
    val seasons: List<ShowsSeasonsModel>,
    val numberOfEpisodes: Int,
    val createdBy: List<ShowsCreatedByModel>,
    val lastEpisodeToAir: ShowsLastEpisodeToAirModel,
    val posterPath: String,
    val backdropPath: String,
    val spokenLanguages: List<String>,
    val productionCompanies: List<ShowsProductionCompaniesModel>,
    val genres: List<GenresModel>,
    val originalName: String,
    val voteAverage: Double,
    val name: String,
    val tagline: String,
    val episodeRunTime: List<Int>,
    val id: Int,
    val numberOfSeasons: Int,
    val nextEpisodeToAir: ShowsNextEpisodeToAirModel,
    val voteCount: Int
)

data class ShowsSeasonsModel(
    val airDate: String,
    val overview: String,
    val episodeCount: Int,
    val name: String,
    val seasonNumber: Int,
    val posterPath:String,
    val id: Int,
)

data class ShowsNextEpisodeToAirModel(
    val airDate: String,
    val overview: String,
    val episodeNumber: Int,
    val name: String,
    val seasonNumber: Int,
    val stillPath:String,
    val id: Int,
) {
    companion object {
        fun empty(): ShowsNextEpisodeToAirModel =
            ShowsNextEpisodeToAirModel(
                Constants.NO_AIRING,
                Constants.NO_OVERVIEW,
                0,
                Constants.NO_NAME,
                0,
                "",
                0
            )
    }
}

data class ShowsCreatedByModel(
    val name: String,
    val profilePath: String,
    val id: Int
)

data class ShowsLastEpisodeToAirModel(
    val airDate: String,
    val overview: String,
    val episodeNumber: Int,
    val name: String,
    val seasonNumber: Int,
    val stillPath:String,
    val id: Int,
) {
    companion object {
        fun empty(): ShowsLastEpisodeToAirModel =
            ShowsLastEpisodeToAirModel(
                Constants.NO_AIRING,
                Constants.NO_OVERVIEW,
                0,
                Constants.NO_NAME,
                0,
                "",
                0
            )
    }
}

data class ShowsProductionCompaniesModel(
    val logoPath: String,
    val name: String,
    val id: Int,
    val originCountry: String
)
