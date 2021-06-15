package com.homindolentrahar.misbar.domain.models

data class MoviesModel(
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val id: Int,
    val title: String,
    val posterPath: String
) {
    var genres: List<GenresModel> = listOf()
}
