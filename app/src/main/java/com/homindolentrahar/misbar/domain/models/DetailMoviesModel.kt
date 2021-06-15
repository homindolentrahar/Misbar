package com.homindolentrahar.misbar.domain.models

data class DetailMoviesModel(
    val overview: String,
    val originalTitle: String,
    val runtime: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val spokenLanguages: List<String>,
    val revenue: Int,
    val productionCompanies: List<MoviesProductionCompaniesModel>,
    val releaseDate: String,
    val genres: List<GenresModel>,
    val voteAverage: Double,
    val productionCountries: List<String>,
    val tagline: String,
    val id: Int,
    val voteCount: Int,
    val budget: Int
)

data class MoviesProductionCompaniesModel(
    val name: String,
    val id: Int,
)
