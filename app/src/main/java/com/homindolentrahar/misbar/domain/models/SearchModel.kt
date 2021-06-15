package com.homindolentrahar.misbar.domain.models

data class SearchModel(
    val mediaType: String,
    val name: String,
    val id: Int,
    val overview: String,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val firstAirDate: String,
) {
    var genres: List<GenresModel> = listOf()
}